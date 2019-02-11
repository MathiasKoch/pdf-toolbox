package com.dong.matko.toolbox.unlocker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.pdf.PdfReader;

public class Unlocker
{

	public static void extractQPDF(){
		String file = new File(Unlocker.class.getClassLoader().getResource("qpdf.zip").getFile()).toString();
		try {
			extract(file, System.getProperty("java.io.tmpdir"));
			final File qpdf = new File(System.getProperty("java.io.tmpdir") + File.separator + "qpdf-5.0.1");
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					try {
						FileUtils.deleteDirectory(qpdf);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean unlock(String lockedFile, boolean delete){      
		File qpdf = new File(System.getProperty("java.io.tmpdir") + File.separator + "qpdf-5.0.1");

		if (!qpdf.exists()){
			extractQPDF();
		}
		try {
			Process p = Runtime.getRuntime().exec(qpdf.getAbsolutePath().toString() + File.separator + "bin" + File.separator + "qpdf.exe --decrypt " + lockedFile + " " + lockedFile.replace(".pdf", "_unlocked.pdf"));
			if(delete){
				p.waitFor();
				File oldFile = new File(lockedFile);
				oldFile.delete();
				File newFile = new File(lockedFile.replace(".pdf", "_unlocked.pdf"));
				newFile.renameTo(oldFile);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void extract(String file, String destination) throws IOException {
		ZipInputStream in = null;
		OutputStream out = null;
		try {
			// Open the ZIP file
			in = new ZipInputStream(new FileInputStream(file));
			// Get the first entry
			ZipEntry entry = null;
			while ((entry = in.getNextEntry()) != null) {
				String outFilename = entry.getName();
				// Open the output file
				if (entry.isDirectory()) {
					new File(destination, outFilename).mkdirs();
				} else {
					out = new FileOutputStream(new File(destination,outFilename));
					// Transfer bytes from the ZIP file to the output file
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					out.close();
				}
			}
		} finally {
			// Close the stream
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	public static boolean isLocked(File lockedFile){
		try {
			PdfReader reader = new PdfReader(lockedFile.getPath());
			if(reader.isEncrypted()){
				reader.close();
				return true;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}