/*
 * 
 * Crystal McDonald
 * Java II
 * 1308
 * Week 2
 * 
 */
package com.cm.clamdiggers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.util.Log;

public class DataFile {
	//cache data internally
	
	
public static String FILE_NAME2 = "tideInfo.txt";
	
public static String JSON_DATA = "type";
public static String JSON_INFO = "tideInfo";
public static String JSON_LOCATION = "tidesite";
public static String JSON_DATE = "pretty";
public static String JSON_SWELL = "height";
	
	//creating a history 
	@SuppressWarnings("resource")
	public static Boolean storeStringFile(Context context, String filename, String content, Boolean external){
		try{
			File file;
			FileOutputStream fos;
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fos = new FileOutputStream(file);
			}else{
				fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			}
			//write content
			fos.write(content.getBytes());
			fos.close();
		} catch(IOException e){
			Log.e("WRITE ERROR", filename);
		}
		return true;
	}
	//store objects on hard drive  getting an object as content
	@SuppressWarnings("resource")
	public static Boolean storeObjectFile(Context context, String filename, Object content, Boolean external){
		try{
			File file;
			FileOutputStream fos;
			ObjectOutputStream oos;
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fos = new FileOutputStream(file);
			}else{
				fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			}
			oos = new ObjectOutputStream(fos);
			//write content
			oos.writeObject(content);
			oos.close();
			fos.close();
		} catch(IOException e){
			Log.e("WRITE ERROR", filename);
		}
		return true;
	}
	
//	@SuppressWarnings("resource")
//	public static String readStringFile(Context context, String filename, Boolean external){
//		String content = "";
//		try{
//			File file;
//			FileInputStream fin;
//			if(external){
//				file = new File(context.getExternalFilesDir(null), filename);
//				fin = new FileInputStream(file);
//			}else{
//				file = new File(filename);
//				fin = context.openFileInput(filename);
//			}
//			BufferedInputStream bin = new BufferedInputStream(fin);
//			byte[] contentBytes = new byte[1024];
//			int bytesRead = 0;
//			StringBuffer contentBuffer = new StringBuffer();
//			
//			while((bytesRead = bin.read(contentBytes)) != -1){
//				content = new String(contentBytes,0,bytesRead);
//				contentBuffer.append(content);
//			}
//			content = contentBuffer.toString();
//			fin.close();
//		}catch (FileNotFoundException e){
//			Log.e("READ ERROR", "FILE NOT FOUND" + filename);
//		}catch (IOException e){
//			Log.e("READ ERROR", "I/O ERROR");
//		}
//		return content;
//	}
	 public static String readStringFile2(Context context, String filename){
         String content = "";
         try{

                 FileInputStream fin = context.openFileInput(filename);
                 BufferedInputStream bin = new BufferedInputStream(fin);
                
                 byte[] contentBytes = new byte[1024];
                 int bytesRead = 0;
                 StringBuffer contentBuffer = new StringBuffer();
                
                 while((bytesRead = bin.read(contentBytes)) != -1){
                         content = new String(contentBytes,0,bytesRead);
                         contentBuffer.append(content);
                 }
                 content = contentBuffer.toString();
                 fin.close();
         } catch (FileNotFoundException e){
                 Log.e("readStringFile", filename + " file not found");
         } catch (IOException e){
                 Log.e("readStringFile", "I/O Error");
         }
         return content;
 }
	@SuppressWarnings("resource")
	public static Object readObjectFile(Context context, String filename, Boolean external){
		Object content = new Object();
		try{
			File file;
			FileInputStream fin;
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fin = new FileInputStream(file);
			}else{
				file = new File(filename);
				fin = context.openFileInput(filename);
			}
			
			ObjectInputStream ois = new ObjectInputStream(fin);
			
			try{
				content = (Object) ois.readObject();
			} catch (ClassNotFoundException e){
				Log.e("READ ERROR", "INVALID JAVA OBJECT FILE");
			}
			ois.close();
			fin.close();
		}catch (FileNotFoundException e){
			Log.e("READ ERROR", "FILE NOT FOUND" + filename);
			return null;
		}catch (IOException e){
			Log.e("READ ERROR", "I/O ERROR");
		}
		return content;
	}
	public static String readStringFile(Context context, String FILE_NAME2,
			boolean external) {
		// TODO Auto-generated method stub
		return null;
	}
}