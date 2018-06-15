package database;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bean.score.DaoMaster;
import bean.score.DaoSession;
import bean.score.Score;
import bean.score.ScoreDao;
import bean.student.Student;
import bean.student.StudentDao;
import shaoqi.myapplication.R;

/**
 * Created by hayden on 18-5-28.
 */

public class GreenDaoDemo extends AppCompatActivity {

    //操作按钮
    private Button add,modify,delete,query;
    //表格显示
    private TableRow tab1,tab2,tab3;
    //数据统计
    private int count;
    //数据填充
    private TextView stu1_id,stu1_name,stu1_age,stu1_describe,
            stu2_id,stu2_name,stu2_age,stu2_describe,
            stu3_id,stu3_name,stu3_age,stu3_describe;
    //操作对象
    StudentDao studentDao;
    ScoreDao scoreDao;
    //数据存储
    List<Student> studentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        //数据准备
        initData();
        //视图相关
        initView();
        //数据库相关
        initDataBase();
    }

    /**
     * 准备数据
     */
    private void initData() {
        studentList = new ArrayList<>();
        Student stu1 = new Student(null,"邵琦",23);
        Student stu2 = new Student(null,"钟伟洪",24);
        studentList.add(stu1);
        studentList.add(stu2);
        count = 0;
    }

    /**
     * 视图类
     */
    private void initView() {
        //增
        add = findViewById(R.id.add_data);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加数据操作
                Toast.makeText(getApplicationContext(),"添加了一条数据",Toast.LENGTH_SHORT).show();
                //插入学生一
                Long id = studentDao.insert(studentList.get(0));
                //获取几个分数
                Score chinese = new Score(null,"chinese",88,id);
                Score math = new Score(null,"math",66,id);
                Score english = new Score(null,"english",92,id);
                scoreDao.insert(chinese);
                scoreDao.insert(math);
                scoreDao.insert(english);
            }
        });
        //改
        modify = findViewById(R.id.modify_data);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改数据操作
//                Toast.makeText(getApplicationContext(),"修改了一条数据",Toast.LENGTH_SHORT).show();
//                Student student = new Student((long) 2,"haha",1001,"改后的数据");
//                studentDao.update(student);
            }
        });
        //删
        delete = findViewById(R.id.delete_data);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据操作
                Toast.makeText(getApplicationContext(),"删除了一条数据",Toast.LENGTH_SHORT).show();
                if(count==3){
                    Student student = studentDao.queryBuilder().
                            where(StudentDao.Properties.Id.eq(3))
                            .build().unique();
                    if(student==null){
                        Toast.makeText(getApplicationContext(),"未找到您要删除的对象",Toast.LENGTH_SHORT).show();
                    }else{
                        studentDao.deleteByKey(student.getId());
                    }
                }
                if(count==2){
                    Student student = studentDao.queryBuilder().
                            where(StudentDao.Properties.Id.eq(2))
                            .build().unique();
                    if(student==null){
                        Toast.makeText(getApplicationContext(),"未找到您要删除的对象",Toast.LENGTH_SHORT).show();
                    }else{
                        studentDao.deleteByKey(student.getId());
                    }
                }
                if(count==1){
                    Student student = studentDao.queryBuilder().
                            where(StudentDao.Properties.Id.eq(1))
                            .build().unique();
                    if(student==null){
                        Toast.makeText(getApplicationContext(),"未找到您要删除的对象",Toast.LENGTH_SHORT).show();
                    }else{
                        studentDao.deleteByKey(student.getId());
                    }
                }
                if(count>0) {
                    count--;
                }
            }
        });
        //查
        query = findViewById(R.id.query_data);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询数据操作
                Toast.makeText(getApplicationContext(),"查询数据操作",Toast.LENGTH_SHORT).show();
                List<Student> list = studentDao.loadAll();
                Student student = list.get(0);
                List<Score> scores = student.getScores();
                for(int i=0;i<scores.size();i++){
                    Log.i("Test",scores.get(i).getType()+":"+scores.get(i).getScore());
                }
            }
        });
        //表格控件
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        //text控件
        stu1_id = findViewById(R.id.stu1_id);
        stu1_name = findViewById(R.id.stu1_name);
        stu1_age = findViewById(R.id.stu1_age);
        stu1_describe = findViewById(R.id.stu1_describe);

        stu2_id = findViewById(R.id.stu2_id);
        stu2_name = findViewById(R.id.stu2_name);
        stu2_age = findViewById(R.id.stu2_age);
        stu2_describe = findViewById(R.id.stu2_describe);

        stu3_id = findViewById(R.id.stu3_id);
        stu3_name = findViewById(R.id.stu3_name);
        stu3_age = findViewById(R.id.stu3_age);
        stu3_describe = findViewById(R.id.stu3_describe);
    }

    /**
     * 隐藏所有视图
     */
    private void hideAllTab(){
        tab1.setVisibility(View.GONE);
        tab2.setVisibility(View.GONE);
        tab3.setVisibility(View.GONE);
    }

    /**
     * 数据库创建
     */
    private void initDataBase() {
        //获取一个DevOpenHelper对象
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "Database1.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        //获取实例操作对象
        studentDao = daoSession.getStudentDao();
        scoreDao = daoSession.getScoreDao();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
