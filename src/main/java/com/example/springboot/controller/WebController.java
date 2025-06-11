package com.example.springboot.controller;



import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Employee;
import com.example.springboot.exception.CustomException;
import com.example.springboot.service.AdminService;
import com.example.springboot.service.ArticleService;
import com.example.springboot.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


//标注这个是接口 返回 json
@RestController()
@Tag(name = "网页接口")
public class WebController {

//    @Resource
    @Autowired
    private EmployeeService employeeService;

//    @Resource
    @Autowired
    private AdminService adminService;
//    @Resource
    @Autowired
    private ArticleService articleService;
//    可以通过get 请求
    @Operation(summary = "返回 hello")
    @GetMapping("/hello")
    public  Result hello() {
        return Result.success("hello");
    }

    @Operation(summary = "返回天气很好")
    @GetMapping("/weather")
    public Result weather() {
        return Result.success("today's weather is sunny! qwq");
    }

    @Operation(summary = "返回程序自己抛出的异常")
    @GetMapping("/count")
    public Result count() {
//        int res=1/0;
        throw new CustomException("400", "error" );
//        throw new  RuntimeException("error");// 程序自己抛出一个异常
//        return Result.success(114514);
    }


    //返回一个 json 数据
    @Operation(summary = "返回一个 Json 数据")
    @GetMapping("/map")
    public Result map() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","zcj");
        map.put("age",18);
        return Result.success(map);
    }

    @Operation(summary = "用户登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody Account account){
        Account result=null;
        if(("ADMIN").equals(account.getRole())){
           result = adminService.login(account);
        }else if(("EMP").equals(account.getRole())){
            result= employeeService.login(account);
        }else {
            return Result.error("400","角色错误");
        }

        return Result.success(result);
    }

    @Operation(summary = "用户注册接口")
    @PostMapping("/register")
    public Result register(@RequestBody Employee employee) {
        employeeService.register(employee);
        return Result.success();
    }

    @Operation(summary = "更新用户密码")
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {


       Account result = null;

//        信息已经传入
        System.out.println(account.getRole());
        System.out.println("ADMIN");
//        System.out.println("EMP");

        if("ADMIN".equals(account.getRole())){
            //System.out.println("now step in");
            adminService.updatePassword(account);
        }else if("EMP".equals(account.getRole())){
            employeeService.updatePassword(account);
        }
        else{
            return Result.error("400","非法角色");
        }
        return Result.success();
    }

    @Operation(summary = "文章线性表")
    @GetMapping("/lineData")
    public Result getLineData() {
        Map<String, Object> map = new HashMap<>();

        Date date = new Date();
        DateTime start = DateUtil.offsetDay(date, -7);
        List<DateTime> dateTimeList = DateUtil.rangeToList(start, date, DateField.DAY_OF_YEAR);
        List<String> dateList = dateTimeList.stream().map(dateTime -> DateUtil.format(dateTime, "MM月dd日"))
                .sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        System.out.println("==============");
        System.out.println(dateList);

        map.put("date", dateList);

        List<Integer> countList = new ArrayList<>();
        for (DateTime day:dateTimeList) {
            String dayFormat = DateUtil.formatDate(day);
            Integer count = articleService.selectCountByDate(dayFormat);
            countList.add(count);
        }

        System.out.println("==============");
        System.out.println(countList);
        map.put("count", countList);
        return Result.success(map);
    }
}
