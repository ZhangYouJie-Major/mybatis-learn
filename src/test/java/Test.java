import com.alibaba.fastjson.JSON;
import dao.TeacherDAO;
import domain.Teacher;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Test {


    @org.junit.Test
    public void test1() throws IOException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream resource = loader.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resource);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        TeacherDAO shiftDao = sqlSession.getMapper(TeacherDAO.class);
        List<Teacher> allTeacher = shiftDao.getAllTeacher();
        System.out.println(JSON.toJSONString(allTeacher));


    }


}
