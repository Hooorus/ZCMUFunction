package ie.calendo.zcmufunction.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Calendo
 * @version 1.0
 * @description 同学互评文件属性
 * @date 2023/9/11 14:01
 */
@Data
@EqualsAndHashCode
public class ClassMates {

    //ID
    private String id;

    //学生名字
    private String name;

    //思想品德模块
    private String SXPD;

    //基础文明模块
    private String JCWM;

    //学号
    private String number;

//    private String remark;

    //同学互评得分
    private String score;

}
