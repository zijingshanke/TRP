package com.fdays.tsms.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
* 
*/ 
public class FileHelper { 
		
	private BufferedReader bufread;    
	private BufferedWriter bufwriter;    
	private File writefile;    
	private String filepath, filecontent, read;    
	private String readStr = "";    
    
    /**
     * 读取文件 
     * @param path
     * @return
     */
    public String readfile(String path) //从文本文件中读取内容      
    {    
        try {    
            filepath = path; //得到文本文件的路径    
            File file = new File(filepath);    
            FileReader fileread = new FileReader(file);    
            bufread = new BufferedReader(fileread);    
            
            StringBuffer str = new StringBuffer();
            while ((read = bufread.readLine()) != null) {    
            	str.append(read);    
            }    
            readStr = str.toString();
            
            fileread.close();
            bufread.close();
            
        } catch (Exception d) {    
            System.out.println(d.getMessage());    
        }    
        return readStr; //返回从文本文件中读取内容    
    }    
   
    /**
     * 向文本文件中写入内容   
     * @param path 		文件完整路径
     * @param content	内容
     * @param append	是否追加
     */ 
    public void writefile(String path, String content, boolean append) {    
        try {    
            boolean addStr = append; //通过这个对象来判断是否向文本文件中追加内容    
            filepath = path; //得到文本文件的路径    
            filecontent = content; //需要写入的内容    
            writefile = new File(filepath);    
            if (writefile.exists() == false) //如果文本文件不存在则创建它     
            {    
                writefile.createNewFile();    
                writefile = new File(filepath); //重新实例化    
            }    
            FileWriter filewriter = new FileWriter(writefile, addStr);    
            bufwriter = new BufferedWriter(filewriter);    
            filewriter.write(filecontent);    
            
            try {
            	filewriter.flush();   
                filewriter.close(); 
                bufwriter.flush();
                bufwriter.close();
			} catch (Exception e) {
				//e.printStackTrace();
			}
        } catch (Exception d) {    
        	d.printStackTrace();
            System.out.println(d.getMessage());    
        }    
    }    
    
    public static void writeMethod2(String filePath,String context)   
    {   
        try  
        {   
            DataOutputStream out=new DataOutputStream(   
                                 new BufferedOutputStream(   
                                 new FileOutputStream(filePath)));   
            //out.writeUTF(context);
            //System.out.println("context==="+context);
            out.write(context.getBytes("UTF-8"));
            out.close();   
        } catch (Exception e)   
        {   
            e.printStackTrace();   
        }   
    }


    /**
     * 根据文件的长路径,返回文件名称,包含后缀
     * @param path
     * @return
     */
    public static String getFileName(String path){
    	if(path!=null && !path.trim().equals("")){
    		String fileName = path.substring(path.lastIndexOf("\\")+1);
    		return fileName;
    	}else{
    		return null;
    	}
    }

    /**
     * 根据文件的长路径或者文件名,返回文件的后缀名称, 如[jpg]
     * @param path
     * @return
     */
    public static String getFileSuffix(String path){
    	if(path!=null && !path.trim().equals("")){
    		if (path.lastIndexOf(".")==-1) return "";
    		else return path.substring(path.lastIndexOf(".")+1);
    	}else{
    		return null;
    	}
    }
    
    /*******************************************************************************
     * 提供文件下载
     * HttpServletRequest request
     * HttpServletResponse response
     * String filepath		[文件的完整路径]
     * String fileShortName [文件名称，包括后缀名称]
     * boolean isDelete		[下载完成之后是否需要删除原文件 true false]
     *******************************************************************************/
    public static void outputFile(HttpServletRequest request,HttpServletResponse response,
			String filepath,String fileShortName,boolean isDelete) throws IOException
	{
		 //String fliname =filepath.substring(filepath.lastIndexOf("/")+1 );
    	//首先判断文件是否存在，不存在则返回
    	File file11 = new File(filepath);
		if(file11.exists() == false){
			System.out.println("下载文件【"+filepath+"】!该文件不存在----------------------");
			return;
		}
		
		
		  BufferedInputStream bufferedInputStream = null;
		  BufferedOutputStream bufferedOutputStream = null;
		  try{
				
				  
			   //由于自带的文件下载选择框，使用的是application/x-www-form-urlencoded格式编码，
			   //需要把把字符串转成application/x-www-form-urlencoded 格式，这里采用UTF-8编码将字符串转到urlencoded 格式
			   String  stringFileName = java.net.URLEncoder.encode(fileShortName,"UTF-8");                 
			   response.reset();
			   //这里contentType的字符编码要与上面一致，才不会出现乱码
			   response.setContentType("application/x-msdownload;charset=UTF-8");//通用的MIME设置  stringFileName  
			   response.setHeader("Content-Disposition","attachment; filename="+stringFileName  );
				
				
				bufferedInputStream = new BufferedInputStream(new FileInputStream(filepath));
				bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
				int read = 0;
				byte[] buffer = new byte[8192];
				while ((read = bufferedInputStream.read(buffer, 0, buffer.length)) != -1) {
					bufferedOutputStream.write(buffer, 0, read);
				}
			 
			  bufferedOutputStream.flush();
			  
			  if(isDelete){
				  File file = new File(filepath);
				  if(file.exists()){
					  file.delete();
					  //System.out.println("文件 "+file.getName()+"--已删除");
				  }
			  }
			  
		  }catch(Exception e){
			  //e.printStackTrace();	
			  System.out.println("下载文件异常："+e.getMessage());
		  }finally{
				try {
					if (bufferedInputStream != null)
						bufferedInputStream.close();
					if (bufferedOutputStream != null)
						bufferedOutputStream.close();
				} catch (IOException ioe2) {
					//ioe2.printStackTrace();
					System.out.println("下载文件异常："+ioe2.getMessage());
				}
		  }
		
	}
} 

