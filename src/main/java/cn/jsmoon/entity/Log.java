package cn.jsmoon.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 日志实体
 * @author: LTQ
 * @create: 2018-10-05 09:34
 **/
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_log")
public class Log {

    public final static String LOGIN_ACTION="登录操作";
    public final static String LOGOUT_ACTION="注销操作";
    public final static String SEARCH_ACTION="查询操作";
    public final static String UPDATE_ACTION="更新操作";
    public final static String ADD_ACTION="添加操作";
    public final static String DELETE_ACTION="删除操作";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     //自增ID

    @Column(length = 100)
    private String type;    //日志类别

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;      //操作用户

    @Column(length = 1000)
    private String content; //操作内容

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using=CustomDateTimeSerializer.class)
    private Date time;      //日志创建日期

    @Transient
    private Date btime; // 起始时间 搜索用到

    @Transient
    private Date etime; // 结束时间 搜索用到

    public Log(String type,String content){
        super();
        this.type = type;
        this.content = content;
    }



}
