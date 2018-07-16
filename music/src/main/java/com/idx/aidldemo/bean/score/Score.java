package com.idx.aidldemo.bean.score;

/**
 * Created by hayden on 18-5-31.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 学生分数类
 */
@Entity
public class Score {
    @Id
    private Long score_id;
    @Property
    private String type;
    @Property
    private int score;
    private Long studentId;
    public Long getStudentId() {
        return this.studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getScore_id() {
        return this.score_id;
    }
    public void setScore_id(Long score_id) {
        this.score_id = score_id;
    }
    @Generated(hash = 1898345220)
    public Score(Long score_id, String type, int score, Long studentId) {
        this.score_id = score_id;
        this.type = type;
        this.score = score;
        this.studentId = studentId;
    }
    @Generated(hash = 226049941)
    public Score() {
    }

}
