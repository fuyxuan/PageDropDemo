package com.example.pagedrop.util;

import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;




import android.util.Log;

public class FileTools {

	public static BufferedReader bufread;
	// ?????��路�????�?
	private static String readStr = "";
	private static String SDPATH = "";

	/**
	 * ??��?????��.
	 * 
	 * @throws IOException
	 * 
	 */
	public static void creatTxtFile(File filename) throws IOException {
		if (!filename.exists()) {
			filename.createNewFile();
			System.err.println(filename + "已�?建�?");
		}
	}

	public static boolean isExists(File file) {
		boolean b = false;
		if (file.exists())
			b = true;
		else
			b = false;
		return b;
	}

	public static void updateFile(String url, String filename, String savepath) {
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		HttpClient client = new DefaultHttpClient();
		try {
			response = client.execute(get);
			HttpEntity entity = response.getEntity();
			long length = entity.getContentLength();
			InputStream is = entity.getContent();
			FileOutputStream fileOutputStream = null;
			if (is != null) {
				File path = new File(savepath);
				if (!path.exists()) {
					path.mkdirs();
				}
				File file = new File(savepath, filename);
				if (!file.exists()) {
					fileOutputStream = new FileOutputStream(file);
					byte[] buf = new byte[1024];
					int ch = -1;
					int count = 0;
					while ((ch = is.read(buf)) != -1) {
						fileOutputStream.write(buf, 0, ch);
						count += ch;
						if (length > 0) {
						}
					}

				}
				fileOutputStream.flush();
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("FileError", e.toString());
		}

	}

	/**
	 * 读�??????��.
	 * 
	 */
	public static String readTxtFile(File filename) {
		String read;
		FileReader fileread;
		try {
			fileread = new FileReader(filename);
			bufread = new BufferedReader(fileread);
			try {
				while ((read = bufread.readLine()) != null) {
					readStr = readStr + read;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("??��??��??" + "\r\n" + readStr);
		return readStr;
	}
	
	/**
	 * 读�??????��.
	 * @throws Exception 
	 * 
	 */
	public static String readTxtFile(InputStream is) throws Exception {
	     int size = is.available();   
	     // Read the entire asset into a local byte buffer.   
	     byte[] buffer = new byte[size];   
	     is.read(buffer);   
	     is.close();   
	     // Convert the buffer into a string.   
	     String text = new String(buffer, "UTF-8");
	     return text;
	}
	
	 

	/**
	 * ???�?
	 * 
	 */
	public static void writeTxtFile(File filename, String newStr)
			throws IOException {
		// ??��??????件�?容�??��?�????????
		String filein = newStr + "\r\n" + readStr + "\r\n";
		RandomAccessFile mm = null;
		try {
			mm = new RandomAccessFile(filename, "rw");
			mm.writeBytes(filein);
		} catch (IOException e1) {
			// TODO ?��???? catch ??
			e1.printStackTrace();
		} finally {
			if (mm != null) {
				try {
					mm.close();
				} catch (IOException e2) {
					// TODO ?��???? catch ??
					e2.printStackTrace();
				}
			}
		}
	}

	/**
	 * �??件中?????��??���???��?为�?�??�?
	 * 
	 * @param oldStr
	 *            ?��???��
	 * @param replaceStr
	 *            ?��???��
	 */
	public static void replaceTxtByStr(File filename, String oldStr,
			String replaceStr) {
		String temp = "";
		try {
			// File file = new File(path);
			FileInputStream fis = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// �??该�???????�?
			for (int j = 1; (temp = br.readLine()) != null
					&& !temp.equals(oldStr); j++) {
				buf = buf.append(temp);
				buf = buf.append(System.getProperty("line.separator"));
			}

			// �??容�?
			buf = buf.append(replaceStr);

			// �??该�???????�?
			while ((temp = br.readLine()) != null) {
				buf = buf.append(System.getProperty("line.separator"));
				buf = buf.append(temp);
			}

			br.close();
			FileOutputStream fos = new FileOutputStream(filename);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
