package ie.calendo.zcmufunction.service.function;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author Calendo
 * @version 1.0
 * @description 同学互评核心算法
 * @date 2023/9/11 14:51
 */
public interface IHupingExcel {

    String uploadFunctionHutool(MultipartFile file,
                                int index_row, String index_col, int score_row,
                                String score_col, int name_row, String name_col,
                                int class_num, boolean self_miss, boolean exclude_up,
                                boolean exclude_dn) throws IOException;

    List<Double> uploadFunctionEasyexcel(MultipartFile file, int num_cnt, int head_row) throws IOException;

}
