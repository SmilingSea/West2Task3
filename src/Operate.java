package PACKAGE_NAME;public class Operate {
  public static void main(String[] args) {
        //测试向数据库中添加学生信息
        insert("7","李贺","男","3","2022-9-1");

        //删除有关学生的全部信息
        delete("6");

        //修改学生的有关信息
        update("5","王勃","1","2022-12-12");

        //查找某班级内全部所有同学的全部信息，通过分页让每一页只显示五个同学的信息
        select("1");





    }








    //插入学生以及相关信息的方法
    public static String insert (String studentid,String name,String sex,String classid,String time) {

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            //获取一个数据库连接
            conn = JdbcUtils.getConnection();
            //通过conn对象获取负责执行SQL命令的Statement对象
            st = conn.createStatement();
            //要执行的SQL命令
            String student_sql = "INSERT INTO `student_manage_system`.`student` (`studentid`, `name`, `sex`) VALUES ('"+studentid+"', '"+name+"', '"+sex+"'); ";
            String class_sql = "INSERT INTO `student_manage_system`.`class` (`studentid`, `classid`, `time`) VALUES ('"+studentid+"', '"+classid+"', '"+time+"'); ";
            //执行插入操作，executeUpdate方法返回成功的条数
            int num1 = st.executeUpdate(student_sql);
            int num2 = st.executeUpdate(class_sql);
            if(num1>0&&num2>0){
                System.out.println("插入成功！！");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //SQL执行完成之后释放相关资源
            JdbcUtils.release(conn, st, rs);
        }
        return "";
    }


    public static String delete (String studentid) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtils.getConnection();
            String sql_student = "delete from student where studentid="+studentid+"";
            String sql_class = "delete from class where studentid="+studentid+"";
            st = conn.createStatement();
            int num1 = st.executeUpdate(sql_student);
            int num2 = st.executeUpdate(sql_class);
            if(num1>0 && num2>0){
                System.out.println("删除成功！！");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.release(conn, st, rs);
        }
        return "";
    }




        public static String update(String studentid,String new_name, String classid ,String time)
        {
            Connection conn = null;
            Statement st = null;
            ResultSet rs = null;
            try{
                conn = JdbcUtils.getConnection();
                String sql_student = "update student set name="+new_name+" where id="+studentid+"";
                String sql_class = "update class set classid="+classid+",time="+time+" where studentid ="+studentid+"";
                st = conn.createStatement();
                int num1 = st.executeUpdate(sql_student);
                int num2 = st.executeUpdate(sql_class);
                if(num1>0||num2>0){
                    System.out.println("更新成功！！");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally{
                JdbcUtils.release(conn, st, rs);
            }
            return "";
        }


    public static String select(String classid) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtils.getConnection();
            String sql = "SELECT s.studentid,name,sex,classid,time\n" +
                    "FROM student s\n" +
                    "INNER JOIN class c\n" +
                    "ON c.studentid = s.studentid\n" +
                    "WHERE classid="+classid+"\n" +
                    "limit 0,5";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                System.out.println("studentid=" + rs.getObject("studentid"));
                System.out.println("name=" + rs.getObject("name"));
                System.out.println("sex=" + rs.getObject("sex"));
                System.out.println("classid=" + rs.getObject("classid"));
                System.out.println("time=" + rs.getObject("time"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.release(conn, st, rs);
        }
        return "";
}
}
