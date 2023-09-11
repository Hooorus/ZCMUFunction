package ie.calendo.zcmufunction.service.function;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import ie.calendo.zcmufunction.entity.ClassMates;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Calendo
 * @version 1.0
 * @description 同学互评核心算法
 * @date 2023/9/11 14:32
 */
@Service
public class HupingExcel implements IHupingExcel {

    public String uploadFunctionHutool(MultipartFile file,
                                       int index_row, String index_col, int score_row,
                                       String score_col, int name_row, String name_col,
                                       int class_num, boolean self_miss, boolean exclude_up,
                                       boolean exclude_dn) throws IOException {
        ArrayList<String> total = new ArrayList<>();
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        int self_cnt = 0;
        if (self_miss) {
            self_cnt = 1;
        } else {
            self_cnt = 0;
        }
        System.out.println("self_cnt: " + self_cnt);
        //需要排除个人最大/最小值
        if (exclude_dn || exclude_up) {
            //班级人数列
            for (int number = 0; number < class_num; number++) {
                ArrayList<Double> list = new ArrayList<>();
                Number index_value = 0;
                String name_value = "";
                //sheet表张
                for (int sheet = 0; sheet < class_num; sheet++) {
                    InputStream inputStream = file.getInputStream();
                    ExcelReader reader = ExcelUtil.getReader(inputStream, sheet);
                    //获取单张表内的number行序号值
                    index_value = (Number) reader.readCellValue(index_col.trim().toUpperCase().charAt(0) - 65, number + index_row - 1);
                    //获取单张表内的number行姓名值
                    name_value = String.valueOf(reader.readCellValue(name_col.trim().toUpperCase().charAt(0) - 65, number + name_row - 1));
                    //获取单张表内的number行评分值
                    Number score_value = (Number) reader.readCellValue(score_col.trim().toUpperCase().charAt(0) - 65, number + score_row - 1);
                    list.add(score_value.doubleValue());
                }
                list.sort(Comparator.naturalOrder());
                System.out.println("list: " + list);
                //去掉最大值
                int flag_up = 0;
                if (exclude_up) {
                    list.remove(0);
                    flag_up = 1;
                }
                //去掉最小值
                int flag_dn = 0;
                if (exclude_dn) {
                    list.remove(class_num - 1 - flag_up);
                    flag_dn = 1;
                }
                Double single_score = 0.0;
                //单人统计
                for (Double item : list) {
                    single_score += item;
                }
                single_score /= (class_num - flag_up - flag_dn);
                treeMap.put(index_value.intValue(), name_value + " " + single_score);
            }
            for (int i = 1; i <= treeMap.size(); i++) {
                total.add(i + ": " + treeMap.get(i));
            }
            //不需排除个人最大/最小值
        } else {
            //sheet表页
            double[] score = new double[class_num + 50];
            System.out.println("score: " + Arrays.toString(score));
            String[] name = new String[class_num + 50];
            System.out.println("name: " + Arrays.toString(name));
            for (int sheet = 0; sheet < class_num; sheet++) {
                InputStream inputStream = file.getInputStream();
                Number index_value;
                ExcelReader reader = ExcelUtil.getReader(inputStream, sheet);
                //班级人数行
                for (int number = 0; number < class_num; number++) {
                    //获取单张表内的number行序号值
                    index_value = (Number) reader.readCellValue(index_col.trim().toUpperCase().charAt(0) - 65, number + index_row - 1);
                    //获取单张表内的number行评分值
                    Number score_value = (Number) reader.readCellValue(score_col.trim().toUpperCase().charAt(0) - 65, number + score_row - 1);
                    score[index_value.intValue()] += score_value.doubleValue();
                }
                System.out.println("score: " + Arrays.toString(score));
            }
            InputStream inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            int head = ((Number) reader.readCellValue(index_col.trim().toUpperCase().charAt(0) - 65, index_row - 1)).intValue();
            for (int i = head; i < head + class_num; i++) {
                String name_value = String.valueOf(reader.readCellValue(name_col.trim().toUpperCase().charAt(0) - 65, i + name_row - 1 - 1));
                name[i] = name_value;
                score[i] /= (class_num - self_cnt);
                total.add(i + ": " + name[i] + " " + score[i]);
            }
            System.out.println("total: " + total);
        }
        String s = JSON.toJSONString(total);
        System.out.println(s);
        return s;
    }

    /**
     * @param file     文件
     * @param num_cnt  数量总计
     * @param head_row 头行
     * @return java.util.List<java.lang.Double>
     * @author Calendo
     * @date 2023/9/11 14:24
     */
    public List<Double> uploadFunctionEasyexcel(MultipartFile file, int num_cnt, int head_row) throws IOException {
        double[] total = new double[num_cnt + 1];
        double anno;
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i <= num_cnt - 1; i++) {
            EasyExcel.read(file.getInputStream(), ClassMates.class, new AnalysisEventListener<ClassMates>() {
                @Override
                public void invoke(ClassMates classMates, AnalysisContext analysisContext) {
                    String score = classMates.getScore();
                    int id = Integer.parseInt(classMates.getId());
                    total[id] += Double.parseDouble(score);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                }
            }).headRowNumber(head_row - 1).sheet(i).doRead();
        }
        for (int i = 1; i <= num_cnt; i++) {
            anno = total[i] / (num_cnt - 1);
            list.add(anno);
        }
        System.out.println(list);
        return list;
    }
}
