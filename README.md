1. ORM 框架

   //数据查询,单条		
		Box box =  _db.getBoxSet().where("xx=?", "33").orderBy("id").first();
		//数据查询,多条
		List<Box> boxList = _db.getBoxSet().toList();
		
		
		//新增
		Box boxNew = new Box();
		 
		_db.getBoxSet().add(boxNew);
		
		//删除,根据id删除
		_db.getBoxSet().delete(5);
		
		//修改
		_db.getBoxSet().update(box);
		
		
		//事务显示使用
		
		try {
			
			
			MybatisUtils.beginTran();
			
			
			MybatisUtils.commit();
		} catch (Exception e) {
			 
			MybatisUtils.rollback();
		}


   
2. 微服务框架

   （1） 接口实现
    @RestController
    @RequestMapping("IBoxInfo")
    public class BoxInfoController implements IBoxInfo {
    
    	
      	@RequestMapping("/getBoxByCode")
      	@Override
      	public Result<String> getBoxByCode(String code) {
      
      
      		
      		return Result.succeed("fdafdas");
      	}
    
    }

   （2）接口
   public interface IBoxInfo {
	
	      Result<String> getBoxByCode(String code);

    }

（3）调用
调用地址：网关地址/api/v1/微服务名称/接口名称/接口方法
![image](https://github.com/hostent/jfcodeDemo/assets/5662806/a9232bb0-fdb9-42f9-b6d2-9fc9cdbe6d42)

 
		
 
