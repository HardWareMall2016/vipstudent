package com.vip.student.login;

import com.google.gson.Gson;
import com.vip.student.login.bean.UserInfoBean;
import com.vip.student.persistobject.TeacherGroupBean;
import com.vip.student.persistobject.UserInfo;
import com.vip.student.utils.Utils;

/**
 * Created by WuYue on 2015/12/18.
 */
public class UserInfoHelp {

    public static void updateUserInfo(UserInfoBean bean, UserInfo user) {
        user.setMobilePhone(bean.getData().getStudentItem().getMobilePhone());
        user.setStudentName(bean.getData().getStudentItem().getStudentName());
        user.setStudentQQ(bean.getData().getStudentItem().getStudentQQ());
        user.setStudyConsultantName(bean.getData().getStudentItem().getStudyConsultantName());
        user.setStudyConsultantTel(bean.getData().getStudentItem().getStudyConsultantTel());
        user.setStudyConsultantEName(bean.getData().getStudentItem().getStudyConsultantEName());
        user.setStudentDays(bean.getData().getStudentItem().getStudentDays());
        user.setFinishCourseCnt(bean.getData().getCourseStatus().getFinishCourseCnt());
        user.setTotalCourseCnt(bean.getData().getCourseStatus().getTotalCourseCnt());
        user.setFirstLessonTime(Utils.parseServerTime(bean.getData().getStudentItem().getFirstLessonTime()));
        user.setLasterLessonTime(Utils.parseServerTime(bean.getData().getStudentItem().getLasterLessonTime()));
        user.setScoreNextDate(Utils.parseServerTime(bean.getData().getStudentItem().getScoreNextDate()));
        user.setStudyConsultantNameSex(bean.getData().getStudentItem().getStudyConsultantNameSex());

        //教师信息
        Gson gson = new Gson();
        TeacherGroupBean teachGroupBean = new TeacherGroupBean();
        teachGroupBean.setTeacherGroup(bean.getData().getTeacherGroup());
        user.setTeacherGroupJsonStr(gson.toJson(teachGroupBean));

        user.setCourseTimeModels(bean.getData().getCourseStatus().getCourseTimeModels());
        user.setIsLogined(true);

        UserInfo.saveLoginUserInfo(user);
    }
}
