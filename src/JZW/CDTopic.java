package JZW;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CDTopic {
	private int cdtopic_id;				//主键
	private String cdtopic_number;		//编号
	private String cdtopic_name;		//名称
	private String cdtopic_keyword;		//关键字
	private String cdtopic_technology;	//实现技术
	private int cdtopic_headcount;		//选题人数
	private boolean cdtopic_active;		//课题是否生效
	private boolean cdtopic_deleted;	//课题是否删除
	private int teacher_id;				//教师外键
	private Date created_at;			//新增时间
    private Date updated_at;			//修改时间
    private Date deleted_at;			//删除时间
    
    public CDTopic() {}
    
    public int getID() {
    	return cdtopic_id;
    }
    
    public void setID(int cdtopic_id) {
    	this.cdtopic_id = cdtopic_id;
    }
    
    public String getNum() {
    	return cdtopic_number;
    }
    
    public void setNum(String cdtopic_number) {
    	this.cdtopic_number = cdtopic_number;
    }
    
    public String getName() {
    	return cdtopic_name;
    }
    
    public void setName(String cdtopic_name) {
    	this.cdtopic_name = cdtopic_name;
    }

    public String getKeyword() {
    	if(cdtopic_keyword==null)	return "";
    	else return cdtopic_keyword;
    }
    
    public void setKeyword(String cdtopic_keyword) {
    	this.cdtopic_keyword = cdtopic_keyword;
    }

    public String getTechnology() {
    	if(cdtopic_technology==null)	return "";
    	else return cdtopic_technology;
    }
    
    public void setTechnology(String cdtopic_technology) {
    	this.cdtopic_technology = cdtopic_technology;
    }

    public int getHeadcount() {
    	return cdtopic_headcount;
    }
    
    public void setHeadcount(int cdtopic_headcount) {
    	this.cdtopic_headcount = cdtopic_headcount;
    }
    
    public String getActiveStr() {
    	if(cdtopic_active == true) {
    		return "已生效";
    	}else {
    		return "未生效";
    	}
    }
    
    public boolean getActive() {
    	return cdtopic_active;
    }
    
    public void setActive(boolean cdtopic_active) {
    	this.cdtopic_active = cdtopic_active;
    }
    
    public String getIsDeleteStr() {
    	if(cdtopic_deleted == true) {
    		return "已删除";
    	}else {
    		return "未删除";
    	}
    }
    
    public boolean getIsDeleted() {
    	return cdtopic_deleted;
    }
    
    public void setIsDeleted(boolean cdtopic_deleted) {
    	this.cdtopic_deleted = cdtopic_deleted;
    }
    
    
    public int getTeacherID() {
    	return teacher_id;
    }
    
    public void setTeacherID(int teacher_id) {
    	this.teacher_id = teacher_id;
    }
    
    public String getTeacherName() {
    	Teacher tea = new Teacher();
    	tea = tea.queryTeacherByID(teacher_id);
    	if(tea.getID()==0)	return "";
    	else	return tea.getName();
    }
    
    public String getCreated() {
        if(created_at!=null) {
        	DateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String Created = t.format(created_at);
            return Created;
    	}else {
    		return "";
    	}
    }
    
    public void setCreated(Date created_at) {
    	this.created_at = created_at;
    }

    public String getUpdated() {
        if(updated_at!=null) {
        	DateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String Updated = t.format(updated_at);
            return Updated;
    	}else {
    		return "";
    	}
    }
    
    public void setUpdated(Date updated_at) {
    	this.updated_at = updated_at;
    }
    
    public String getDeleted() {
    	if(deleted_at!=null) {
    		DateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String Deleted = t.format(deleted_at);
            return Deleted;
    	}else {
    		return "";
    	}
    }
    
    public void setDeleted(Date deleted_at) {
    	this.deleted_at = deleted_at;
    }
    
    //添加课题信息
  	public boolean createCDTopic(String cdtopic_number,String cdtopic_name,String cdtopic_keyword,String cdtopic_technology,int teacher_id, Date created_at) {
      	String insertSql = 
          		"insert into cdtopic("
          			+ "cdtopic_number,"
          			+ "cdtopic_name,"
          			+ "cdtopic_keyword,"
          			+ "cdtopic_technology,"
          			+ "teacher_id,"
  	        		+ "created_at) "
          		+ "values(?,?,?,?,?,?)";
      	Connection conn = conn();
      	PreparedStatement ps = null;
      	try {
      		ps = conn.prepareStatement(insertSql);
      		ps.setString(1, cdtopic_number);
      		ps.setString(2, cdtopic_name);
      		if(!cdtopic_keyword.equals(""))	ps.setString(3, cdtopic_keyword);	else	ps.setNull(3, Types.VARCHAR);
      		if(!cdtopic_technology.equals(""))	ps.setString(4, cdtopic_technology);	else 	ps.setNull(4, Types.VARCHAR);
      		if(teacher_id != 0)	ps.setInt(5, teacher_id);	else	ps.setNull(5, Types.INTEGER);
      		ps.setTimestamp(6, new Timestamp(created_at.getTime()));
      		ps.executeUpdate();
      		return true;
      	} catch (Exception e1) {
      		e1.printStackTrace();
      		System.out.println("添加课题信息失败！");
      		return false;
      	} finally {
      		try {
      			ps.close();
      			conn.close();
      		} catch (SQLException e1) {
      			e1.printStackTrace();
      		}
      	}
      }
    
  	//删除课题信息
    public boolean deleteCDTopic(int cdtopic_id) {
        Connection conn = conn();
        String deleteSQL = "delete from cdtopic where cdtopic_id=" + cdtopic_id;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(deleteSQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据库语句检查或执行出错");
            return false;
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
 
    //查询课题信息
    public List<CDTopic> queryCDTopic(String querySql) {
        List<CDTopic> cdtList = new ArrayList<CDTopic>();
        CDTopic cdt = null;
        ResultSet queryRS = null;
        PreparedStatement queryStatement = null;
        Connection queryConn = null;
        try{
            queryConn = conn();
            queryStatement = queryConn.prepareStatement(querySql);
            queryRS = queryStatement.executeQuery();
            while(queryRS.next()) {
                cdt = new CDTopic();
                cdt.cdtopic_id = queryRS.getInt("cdtopic_id");
            	cdt.cdtopic_number = queryRS.getString("cdtopic_number");
            	cdt.cdtopic_name = queryRS.getString("cdtopic_name");
            	cdt.cdtopic_keyword = queryRS.getString("cdtopic_keyword");
            	cdt.cdtopic_technology = queryRS.getString("cdtopic_technology");
            	cdt.cdtopic_headcount = queryRS.getInt("cdtopic_headcount");
            	cdt.cdtopic_active = queryRS.getBoolean("cdtopic_active");
            	cdt.cdtopic_deleted = queryRS.getBoolean("cdtopic_deleted");
            	cdt.teacher_id = queryRS.getInt("teacher_id");
            	cdt.created_at = queryRS.getTimestamp("created_at");
            	cdt.updated_at = queryRS.getTimestamp("updated_at");
            	cdt.deleted_at = queryRS.getTimestamp("deleted_at");
                cdtList.add(cdt);
            }
        }catch(Exception e2) {
            e2.printStackTrace();
            return null;
        }finally{
            try {
                queryRS.close();
                queryStatement.close();
                queryConn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return cdtList;
    }
    
    //查询课题信息（按主键查找）
    public CDTopic queryCDTopicByID(int cdtopic_id) {
        String querySql = "select * from cdtopic where cdtopic_id="+"'"+cdtopic_id+"'";
        if(queryCDTopic(querySql).size()!=0) {
        	return queryCDTopic(querySql).get(0);
        }else {
        	return new CDTopic();
        }
    }

    //查询课题信息（按编号查找）
    public List<CDTopic> queryCDTopicByNumber(String cdtopic_number) {
        String querySql = "select * from cdtopic where cdtopic_number="+"'"+cdtopic_number+"'";
        return queryCDTopic(querySql);
    }
    
    //查询课题信息（按名称查找）
    public List<CDTopic> queryCDTopicByName(String cdtopic_name) {
        String querySql = "select * from cdtopic where cdtopic_name="+"'"+cdtopic_name+"'";
        return queryCDTopic(querySql);
    }
    
    //查询课题信息（按关键字查找）
    public List<CDTopic> queryCDTopicByKeyword(String cdtopic_keyword){
    	List<CDTopic> cdtList = new ArrayList<CDTopic>();
    	CDTopic cdt = null;
        ResultSet queryRS = null;
        Statement queryStatement = null;
        Connection queryConn = null;
        try{
            queryConn = conn();
            queryStatement = queryConn.createStatement();
            String querySql = "select * from cdtopic";
            queryRS = queryStatement.executeQuery(querySql);
            while(queryRS.next()) {
            	if(queryRS.getString("cdtopic_keyword") != null && queryRS.getString("cdtopic_keyword").indexOf(cdtopic_keyword) != -1
            		&& !cdtopic_keyword.equals("、")) {
            		cdt = new CDTopic();
            		cdt.cdtopic_id = queryRS.getInt("cdtopic_id");
                	cdt.cdtopic_number = queryRS.getString("cdtopic_number");
                	cdt.cdtopic_name = queryRS.getString("cdtopic_name");
                	cdt.cdtopic_keyword = queryRS.getString("cdtopic_keyword");
                	cdt.cdtopic_technology = queryRS.getString("cdtopic_technology");
                	cdt.cdtopic_headcount = queryRS.getInt("cdtopic_headcount");
                	cdt.cdtopic_active = queryRS.getBoolean("cdtopic_active");
                	cdt.cdtopic_deleted = queryRS.getBoolean("cdtopic_deleted");
                	cdt.teacher_id = queryRS.getInt("teacher_id");
                	cdt.created_at = queryRS.getTimestamp("created_at");
                	cdt.updated_at = queryRS.getTimestamp("updated_at");
                	cdt.deleted_at = queryRS.getTimestamp("deleted_at");
                    cdtList.add(cdt);
            	}
            }
            return cdtList;
        }catch(Exception e2) {
            e2.printStackTrace();
            return null;
        }finally{
            try {
                queryRS.close();
                queryStatement.close();
                queryConn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    //查询课题信息（按实现技术查找）
    public List<CDTopic> queryCDTopicByTechnology(String cdtopic_technology) {
        String querySql = "select * from cdtopic where cdtopic_technology="+"'"+cdtopic_technology+"'";
        return queryCDTopic(querySql);
    }
    
    //查询课题信息（按所属教师查找）
    public List<CDTopic> queryCDTopicByTeacherID(int teacher_id) {
        String querySql = "select * from cdtopic where teacher_id="+"'"+teacher_id+"'";
        return queryCDTopic(querySql);
    }
    
    //查询课题信息（按是否已删除查找）
    public List<CDTopic> queryCDTopicByIsDeleted(boolean cdtopic_deleted) {
    	int deleted = cdtopic_deleted?1:0;
    	String querySql = "select * from cdtopic where cdtopic_deleted="+"'"+deleted+"'";
        return queryCDTopic(querySql);
    }
    
    //修改课题信息
    public boolean updateCDTopic(CDTopic cdt,String cdtopic_number,String cdtopic_name,String cdtopic_keyword,String cdtopic_technology,int teacher_id,Date updated_at) {
 
    	int count = 0;//记录是否有修改
        Connection conn = conn();
        //信息有改动才提交
        if(cdtopic_number.equals(cdt.cdtopic_number) == false) {
        	count++;
            String updateSql = "update cdtopic set cdtopic_number='"+cdtopic_number+"' where cdtopic_id=" + cdt.cdtopic_id;
            try {
                PreparedStatement ps = conn.prepareStatement(updateSql);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            }
        }
        
        if(cdtopic_name.equals(cdt.cdtopic_name) == false) {
        	count++;
            String updateSql = "update cdtopic set cdtopic_name='"+cdtopic_name+"' where cdtopic_id=" + cdt.cdtopic_id;
            try {
                PreparedStatement ps = conn.prepareStatement(updateSql);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            }
        }
    
        if(!(cdtopic_keyword.equals("")&&cdt.cdtopic_keyword == null)&&cdtopic_keyword.equals(cdt.cdtopic_keyword) == false) {
        	count++;
        	String updateSql = null;
        	if(cdtopic_keyword.equals(""))
        		updateSql = "update cdtopic set cdtopic_keyword=null where cdtopic_id=" + cdt.cdtopic_id;
        	else
        		updateSql = "update cdtopic set cdtopic_keyword='"+cdtopic_keyword+"' where cdtopic_id=" + cdt.cdtopic_id;
            try {
                PreparedStatement ps = conn.prepareStatement(updateSql);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            }
        }
        
        if(!(cdtopic_technology.equals("")&&cdt.cdtopic_technology == null)&&cdtopic_technology.equals(cdt.cdtopic_technology) == false) {
        	count++;
        	String updateSql = null;
        	if(cdtopic_technology.equals(""))
        		updateSql = "update cdtopic set cdtopic_technology=null where cdtopic_id=" + cdt.cdtopic_id;
        	else
        		updateSql = "update cdtopic set cdtopic_technology='"+cdtopic_technology+"' where cdtopic_id=" + cdt.cdtopic_id;
            try {
                PreparedStatement ps = conn.prepareStatement(updateSql);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            }
        }
        
        if(teacher_id != cdt.teacher_id) {
        	count++;
        	String updateSql = null;
        	if(teacher_id == 0)
        		updateSql = "update cdtopic set teacher_id=null where cdtopic_id=" + cdt.cdtopic_id;
        	else
        		updateSql = "update cdtopic set teacher_id='"+teacher_id+"' where cdtopic_id=" + cdt.cdtopic_id;
            try {
                PreparedStatement ps = conn.prepareStatement(updateSql);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            }
        }
        
        if(updated_at != cdt.updated_at&&count != 0) {
        	String updateSql = "update cdtopic set updated_at = ? where cdtopic_id="+ cdt.cdtopic_id;
            try {
                PreparedStatement ps = conn.prepareStatement(updateSql);
                ps.setTimestamp(1, new Timestamp(updated_at.getTime()));
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            }
        }
        
        try {
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return true;
    }
    
    //学生表所选课题信息改变时改变课题表中的选题人数
    public boolean updadeHeadcount(int cdtopic_id,boolean add) {
    	if(cdtopic_id > 0) {
	    	Connection conn = conn();
	       	CDTopic cdt = new CDTopic();
	       	cdt = cdt.queryCDTopicByID(cdtopic_id);
	       	//判断是增加还是减少
	       	String updateSql = null;
	        if(add == true) {
	        	updateSql = "update cdtopic set cdtopic_headcount ='"+(cdt.getHeadcount()+1)+"' where cdtopic_id=" + cdtopic_id;
	        }else {
	        	updateSql = "update cdtopic set cdtopic_headcount ='"+(cdt.getHeadcount()-1)+"' where cdtopic_id=" + cdtopic_id;
	        }
            try {
                PreparedStatement ps = conn.prepareStatement(updateSql);
                ps.executeUpdate();
                ps.close();
                conn.close();
                return true;
                } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
                }
            }else	return false;
    	}

    //刷新课题信息人员数为实际选题人数
    public boolean refreshHeadcountByID(int cdtopic_id) {
    	if(cdtopic_id > 0) {
    		Connection conn = conn();
    		Student stu = new Student();
        	String updateSql = "update cdtopic set cdtopic_headcount='"+stu.queryStudentByCDTopic(cdtopic_id).size()+"' where cdtopic_id=" + cdtopic_id;
            try {
                PreparedStatement ps = conn.prepareStatement(updateSql);
                ps.executeUpdate();
                ps.close();
                conn.close();
                return true;
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            } 
    	}else	return false;
    }
    
    //刷新所有课题信息人员数为实际选题人数
    public boolean refreshHeadcountOfAll() {
    	Connection conn = conn();
    	Student stu = new Student();
    	List<CDTopic> cdtList = getCDTopicInfo();
		for(CDTopic cdtopic:cdtList) {
			String updateSql = "update cdtopic set cdtopic_headcount='"+stu.queryStudentByCDTopic(cdtopic.getID()).size()+"' where cdtopic_id=" + cdtopic.getID();
			try {
                PreparedStatement ps = conn.prepareStatement(updateSql);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            } 
		}
		try {
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    //判断编号是否存在
    public boolean isExist_number(String cdtopic_number) {
    	if(queryCDTopicByNumber(cdtopic_number).size() != 0)	return true;
    	else return false;
    }

    //获取数据量
    public int CountByString(String querySql) {
    	int count;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;
        try{
            conn = conn();
            ps = conn.prepareStatement(querySql);
            rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
        }catch(Exception e2) {
            e2.printStackTrace();
            return 0;
        }finally{
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return count;
    }

    //获取总数据条数（是否删除+是否有效+所属教师）（2表示不考虑该条件）
    public int Count(int cdtopic_deleted,int cdtopic_active,int teacher_id) {
    	switch(cdtopic_deleted) {
    	case 2:
    		switch(cdtopic_active) {
    		case 2:
    			if(teacher_id!=0) {
    				return CountByString("select count(*) from cdtopic where teacher_id ="+teacher_id);
    			}else {
    				return CountByString("select count(*) from cdtopic");
    			}
			default:
				if(teacher_id!=0) {
    				return CountByString("select count(*) from cdtopic where cdtopic_active = "+cdtopic_active+" and teacher_id ="+teacher_id);
    			}else {
    				return CountByString("select count(*) from cdtopic where cdtopic_active = "+cdtopic_active);
    			}
    		}
    	default:
    		switch(cdtopic_active) {
    		case 2:
    			if(teacher_id!=0) {
    				return CountByString("select count(*) from cdtopic where cdtopic_deleted = "+cdtopic_deleted+" and teacher_id ="+teacher_id);
    			}else {
    				return CountByString("select count(*) from cdtopic where cdtopic_deleted = "+cdtopic_deleted);
    			}
			default:
				if(teacher_id!=0) {
    				return CountByString("select count(*) from cdtopic where cdtopic_deleted = "+cdtopic_deleted+" and cdtopic_active = "+cdtopic_active+" and teacher_id ="+teacher_id);
    			}else {
    				return CountByString("select count(*) from cdtopic where cdtopic_deleted = "+cdtopic_deleted+" and cdtopic_active = "+cdtopic_active);
    			}
    		}
    	}
    }

    //返回分页数据
    public List<CDTopic> cutPageDataByString(int page,int pageSize,String querySql){
        List<CDTopic> cdtList = new ArrayList<CDTopic>();
        CDTopic cdt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;
        try{
        	conn = conn();
            ps = conn.prepareStatement(querySql);
            ps.setInt(1,page*pageSize-pageSize);
    		ps.setInt(2,pageSize);
            rs = ps.executeQuery();
            while(rs.next()) {
            	cdt = new CDTopic();
            	cdt.cdtopic_id = rs.getInt("cdtopic_id");
            	cdt.cdtopic_number = rs.getString("cdtopic_number");
            	cdt.cdtopic_name = rs.getString("cdtopic_name");
            	cdt.cdtopic_keyword = rs.getString("cdtopic_keyword");
            	cdt.cdtopic_technology = rs.getString("cdtopic_technology");
            	cdt.cdtopic_headcount = rs.getInt("cdtopic_headcount");
            	cdt.cdtopic_active = rs.getBoolean("cdtopic_active");
            	cdt.cdtopic_deleted = rs.getBoolean("cdtopic_deleted");
            	cdt.teacher_id = rs.getInt("teacher_id");
            	cdt.created_at = rs.getTimestamp("created_at");
            	cdt.updated_at = rs.getTimestamp("updated_at");
            	cdt.deleted_at = rs.getTimestamp("deleted_at");
                cdtList.add(cdt);
            }
        }catch(Exception e2) {
            e2.printStackTrace();
            return null;
        }finally{
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return cdtList;
    }
    
    //返回分页数据（是否删除+是否有效+所属教师）（2表示不考虑该条件）
    public List<CDTopic> cutPageData(int page,int pageSize,int cdtopic_deleted,int cdtopic_active,int teacher_id){
    	switch(cdtopic_deleted) {
    	case 2:
    		switch(cdtopic_active) {
    		case 2:
    			if(teacher_id!=0) {
    				return cutPageDataByString(page,pageSize,"select * from cdtopic where teacher_id = "+teacher_id+" limit ?,?");
    			}else {
    				return cutPageDataByString(page,pageSize,"select * from cdtopic limit ?,?");
    			}
			default:
				if(teacher_id!=0) {
    				return cutPageDataByString(page,pageSize,"select * from cdtopic where cdtopic_active = "+cdtopic_active+" and teacher_id ="+teacher_id+" limit ?,?");
    			}else {
    				return cutPageDataByString(page,pageSize,"select * from cdtopic where cdtopic_active = "+cdtopic_active+" limit ?,?");
    			}
    		}
    	default:
    		switch(cdtopic_active) {
    		case 2:
    			if(teacher_id!=0) {
    				return cutPageDataByString(page,pageSize,"select * from cdtopic where cdtopic_deleted = "+cdtopic_deleted+" and teacher_id ="+teacher_id+" limit ?,?");
    			}else {
    				return cutPageDataByString(page,pageSize,"select * from cdtopic where cdtopic_deleted = "+cdtopic_deleted+" limit ?,?");
    			}
			default:
				if(teacher_id!=0) {
    				return cutPageDataByString(page,pageSize,"select * from cdtopic where cdtopic_deleted = "+cdtopic_deleted+" and cdtopic_active = "+cdtopic_active+" and teacher_id ="+teacher_id+" limit ?,?");
    			}else {
    				return cutPageDataByString(page,pageSize,"select * from cdtopic where cdtopic_deleted = "+cdtopic_deleted+" and cdtopic_active = "+cdtopic_active+" limit ?,?");
    			}
    		}
    	}
    }
    
    //从数据库获取所有课题信息返回课题表
    public List<CDTopic> getCDTopicInfo() {
        List<CDTopic> CDTList = new ArrayList<CDTopic>();
        CDTopic cdt = null;
        ResultSet queryRS = null;
        PreparedStatement queryStatement = null;
        Connection queryConn = null;
        try{
            queryConn = conn();
            String sqlQuery = "select * from cdtopic";
            queryStatement = queryConn.prepareStatement(sqlQuery);
            queryRS = queryStatement.executeQuery();
            while(queryRS.next()) {
            	cdt = new CDTopic();
            	cdt.cdtopic_id = queryRS.getInt("cdtopic_id");
            	cdt.cdtopic_number = queryRS.getString("cdtopic_number");
            	cdt.cdtopic_name = queryRS.getString("cdtopic_name");
            	cdt.cdtopic_keyword = queryRS.getString("cdtopic_keyword");
            	cdt.cdtopic_technology = queryRS.getString("cdtopic_technology");
            	cdt.cdtopic_headcount = queryRS.getInt("cdtopic_headcount");
            	cdt.cdtopic_active = queryRS.getBoolean("cdtopic_active");
            	cdt.cdtopic_deleted = queryRS.getBoolean("cdtopic_deleted");
            	cdt.teacher_id = queryRS.getInt("teacher_id");
            	cdt.created_at = queryRS.getTimestamp("created_at");
            	cdt.updated_at = queryRS.getTimestamp("updated_at");
            	cdt.deleted_at = queryRS.getTimestamp("deleted_at");
            	CDTList.add(cdt);
            }
            return CDTList;
        }catch(Exception e2) {
            e2.printStackTrace();
            return null;
        }finally{
            try {
                queryRS.close();
                queryStatement.close();
                queryConn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    //连接数据库
    public Connection conn() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost/csms?useSSL=true&serverTimezone=Asia/Shanghai&user=root&password=root";
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return null;
    }
    
}