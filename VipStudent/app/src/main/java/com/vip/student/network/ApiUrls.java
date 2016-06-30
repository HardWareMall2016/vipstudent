package com.vip.student.network;

public class ApiUrls {
    //User - 用户登录
    public static final String USER_LOGIN = "LoginService";
    public static final String GET_USERINFO = "GetStudentFullinfo";

    //Message - 学生消息
    public static final String GET_STUDENT_MSG = "GetStudentMessage";
    //Message - 学生消息标记已读
    public static final String SET_MSG_READ = "SetMessageRead";
    //Message - 获取推送消息
    public static final String GET_PUSH_MSG = "GetPushMessage";
    //Message - 设置推送消息已读
    public static final String SET_PUSH_MSG_READ = "SetPushMessageRead";

    //修改密码
    public static final String CHANGPASSWORD = "ChangePassword";
    //获取学生课表
    public static final String GETSTUDEENTLESSON = "GetStudentLesson";
    //调课申请
    public static final String  CHANGELESSONREQUEST = "ChangeLessonRequest";
    //添加教师评价
    public static final String  SETTEACHERAPPRAISER = "SetTeacherAppraise";
    //获取提醒时间
    public static final String  GETPUSHSTATE = "GetPushState";
    //设置提醒时间
    public static final String  SETPUSHSTATE = "SetPushState";
    //调课申请限制
    public static final String  GETCHANGELESSON = "GetChangeLesson";

    //意见反馈
    public static final String  TEACHER_FEEDBACK = "http://userfeedback.tpooo.com/appmsg/api/message/addMessage.do";
}
