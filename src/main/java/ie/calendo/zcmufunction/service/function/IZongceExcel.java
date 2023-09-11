package ie.calendo.zcmufunction.service.function;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Calendo
 * @version 1.0
 * @description 综测统计核心算法
 * @date 2023/9/11 14:52
 */
public interface IZongceExcel {

    String uploadFunction(MultipartFile file, Integer majorStart) throws Exception;

}
