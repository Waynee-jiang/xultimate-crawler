package org.danielli.xultimate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.crypto.CipherUtils;
import org.danielli.xultimate.util.crypto.SymmetricAlgorithms;
import org.danielli.xultimate.util.io.FileUtils;
import org.danielli.xultimate.util.io.IOUtils;

public class JavaEncrypt {
	
	public static void main(String[] args) throws IOException {
		File parentDirectory = new File("/home/toc/Documents/git/xultimate-crawler/src/test/java/alibaba");
		// 加密
//		for (File file :parentDirectory.listFiles()) {
//			if (file.isDirectory()) {
//				continue;
//			}
//			FileInputStream tmpInput = FileUtils.openInputStream(new File(parentDirectory, file.getName()));
//			FileOutputStream tmpOutput = FileUtils.openOutputStream(new File(parentDirectory, "Encrypt" + file.getName()));
//			CipherUtils.encrypt(SymmetricAlgorithms.AES.getCipher(), SymmetricAlgorithms.AES.getKey(""), tmpInput, tmpOutput);
//			IOUtils.closeQuietly(tmpInput);
//			IOUtils.closeQuietly(tmpOutput);
//		}
		
		// 解密
		for (File file :parentDirectory.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}
			FileInputStream tmpInput = FileUtils.openInputStream(new File(parentDirectory, file.getName()));
			FileOutputStream tmpOutput = FileUtils.openOutputStream(new File(parentDirectory, StringUtils.replace(file.getName(), "Encrypt", "")));
			CipherUtils.decrypt(SymmetricAlgorithms.AES.getCipher(), SymmetricAlgorithms.AES.getKey(""), tmpInput, tmpOutput);
			IOUtils.closeQuietly(tmpInput);
			IOUtils.closeQuietly(tmpOutput);
		}
	}
	
}
