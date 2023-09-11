package ie.calendo.zcmufunction.controller;

import ie.calendo.zcmufunction.common.R;
import ie.calendo.zcmufunction.service.function.IZongceExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author Calendo
 * @version 1.0
 * @description 综测控制层模块
 * @date 2023/9/11 14:17
 */
@CrossOrigin
@RestController
@RequestMapping("/zongce")
public class ZongceExcelReader {

    @Autowired
    private IZongceExcel zongceExcel;

    @PostMapping("/upload")
    public R upload(MultipartFile file, @RequestParam(value = "major_start") Integer majorStart) throws Exception {
        String result = zongceExcel.uploadFunction(file, majorStart);
        if (result != null) {
            return R.success(200, "综测计算成功", new Date(), result);
        } else {
            return R.error(500, "Server Error", new Date());
        }
    }

}
