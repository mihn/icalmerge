import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

import java.io.*;
import java.util.List;

public class Merger {
	public static void main(String[] args) throws IOException {
		String inputDir = ".";
		String outputFile = "merged.ics";
		if (args.length >= 1) {
			inputDir = args[0];
			if (args.length == 2) {
				outputFile = args[1];
			}
		}
		File dir = new File(inputDir);
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ics");
			}
		});
		ICalendar main = new ICalendar();
		for (File file : files) {
			InputStream inputStream = new FileInputStream(file);
			List<ICalendar> all = Biweekly.parse(inputStream).all();
			for (ICalendar iCalendar : all) {
				for (VEvent event : iCalendar.getEvents()) {
					main.addEvent(event);
				}
			}
		}
		File file = new File(outputFile);
		file.createNewFile();
		Biweekly.write(main).go(file);
	}
}
