package ie.calendo.zcmufunction.controller;

import ie.calendo.zcmufunction.common.R;
import ie.calendo.zcmufunction.service.function.IHupingExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description 同学互评统计模块(hutool技术和easyexcel技术)
 * @date 2023/9/11 14:19
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/huping")
public class HupingExcelCounter {

    @Autowired
    private IHupingExcel hupingExcel;

    /**
     * @param file       待计算的同学互评表格
     * @param index_row  序号行
     * @param index_col  序号列
     * @param score_row  评分行
     * @param score_col  评分列
     * @param name_row   姓名行
     * @param name_col   姓名列
     * @param class_num  班级人数（对应的是sheet表的总量）
     * @param self_miss  忽略自评与否 true：忽略；false：不忽略
     * @param exclude_up 得分去最大值 true：去除；false：不去除
     * @param exclude_dn 得分去最小值 true：去除；false：不去除
     * @return ArrayList<String>
     */
    @PostMapping("/upload")
    public R upload(MultipartFile file,
                    @RequestParam(value = "index_row") int index_row,
                    @RequestParam(value = "index_col") String index_col,
                    @RequestParam(value = "score_row") int score_row,
                    @RequestParam(value = "score_col") String score_col,
                    @RequestParam(value = "name_row") int name_row,
                    @RequestParam(value = "name_col") String name_col,
                    @RequestParam(value = "class_num") int class_num,
                    @RequestParam(value = "self_miss") boolean self_miss,
                    @RequestParam(value = "exclude_up") boolean exclude_up,
                    @RequestParam(value = "exclude_dn") boolean exclude_dn) throws IOException {

        String result = hupingExcel.uploadFunctionHutool(file, index_row, index_col, score_row, score_col, name_row, name_col, class_num, self_miss, exclude_up, exclude_dn);
        if (result != null) {
            return R.success(200, "同学互评计算成功", new Date(), result);
        } else {
            return R.error(500, "Server Error", new Date());
        }
    }

    @PostMapping("/upload2")
    public R upload(MultipartFile file,
                    @RequestParam(value = "num_cnt") int num_cnt,
                    @RequestParam(value = "head_row") int head_row) throws IOException {

        List<Double> result = hupingExcel.uploadFunctionEasyexcel(file, num_cnt, head_row);
        if (result != null) {
            return R.success(200, "同学互评计算成功", new Date(), result);
        } else {
            return R.error(500, "Server Error", new Date());
        }
    }
}
