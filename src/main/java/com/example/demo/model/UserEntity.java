package com.example.demo.model;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.listener.UserDataListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * UserEntity
 *
 * @author wenbiao
 * @date 2022.08.05 下午 3:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @ExcelProperty(value = "姓名")
    private String name;

    @ExcelProperty(value = "年龄")
    private int age;

    @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "操作时间")
    private Date time;

    // easyExcel官网：https://easyexcel.opensource.alibaba.com/
    public static void main(String[] args) {
        // 导出
        // List<UserEntity> dataList = new ArrayList<>();
        // for (int i = 0; i < 10; i++) {
        //     UserEntity userEntity = new UserEntity();
        //     userEntity.setName("张三" + i);
        //     userEntity.setAge(20 + i);
        //     userEntity.setTime(new Date(System.currentTimeMillis() + i));
        //     dataList.add(userEntity);
        // }
        // EasyExcel.write("C:/Users/wenbiao/Desktop/easyexcel-user1.xlsx", UserEntity.class).sheet("用户信息").doWrite(dataList);

        // 导入
        String filePath = "C:/Users/wenbiao/Desktop/easyexcel-user1.xlsx";
        UserDataListener userDataListener = new UserDataListener();
        EasyExcel.read(filePath, userDataListener).sheet().doRead();
        System.out.println("表头：" + JSONArray.toJSONString(userDataListener.getHeadList()));
        System.out.println("数据体：" + JSONArray.toJSONString(userDataListener.getDataList()));
    }

}
