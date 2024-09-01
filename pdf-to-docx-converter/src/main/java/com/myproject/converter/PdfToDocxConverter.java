package com.myproject.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class PdfToDocxConverter {

	public static void main(String[] args) {
		String pdfFile = "E:/PDF/pdf-to-docx-converter/example.pdf";
		String docxFile = "E:/PDF/pdf-to-docx-converter/output.docx";

		try {
			File pdf = new File(pdfFile);
			File script = new File("E:/PDF/pdf_to_docx_converter.py");

			if (!pdf.exists() || !script.exists()) {
				System.err.println("PDF file or Python script does not exist.");
				return;
			}

			ProcessBuilder pb = new ProcessBuilder("python", script.getAbsolutePath(), pdfFile, docxFile);
			pb.redirectErrorStream(true);
			Process process = pb.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			while ((line = errorReader.readLine()) != null) {
				System.err.println(line);
			}

			int exitCode = process.waitFor();
			System.out.println("Conversion completed with exit code: " + exitCode);
		} catch (IOException e) {
			System.err.println("Error occurred while executing the Python script.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.err.println("The conversion process was interrupted.");
			e.printStackTrace();
		}
	}
}
