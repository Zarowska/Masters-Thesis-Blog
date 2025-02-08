package blog.cirkle.app;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.io.ClassPathResource;

record Usage(String timestamp, double usr, double sys, double idl, double wai, double stl, int used, int free, int buff,
		int cache) {
}

public class UsageParser {

	public static void main(String[] args) throws IOException {
		ClassPathResource resource = new ClassPathResource("usage.csv");
		String content = resource.getContentAsString(Charset.defaultCharset());
		List<Usage> parsed = Arrays.stream(content.split("\n")).map(line -> line.split(","))
				.filter(it -> it.length >= 10).map(parts -> {
					return new Usage(parts[0], parseDouble(parts[1]), parseDouble(parts[2]), parseDouble(parts[3]),
							parseDouble(parts[4]), parseDouble(parts[5]), parseInt(parts[6]), parseInt(parts[7]),
							parseInt(parts[8]), parseInt(parts[9]));
				}).toList();

		int cpuAvg = (int) parsed.stream().mapToDouble(it -> 100.0 - it.idl()).average().getAsDouble();
		int cpuPeak = (int) parsed.stream().mapToDouble(it -> 100.0 - it.idl()).max().getAsDouble();
		int memAvg = (int) parsed.stream().mapToInt(it -> it.used()).average().getAsDouble();
		int memPeak = parsed.stream().mapToInt(it -> it.used()).max().getAsInt();

		System.out.println("CPU Avg: %d, CPU Peak: %d, Memory Avg: %d, Memory Peak: %d".formatted(cpuAvg, cpuPeak,
				memAvg / 1024, memPeak / 1024));

	}

	private static int parseInt(String str) {
		return str.isBlank() ? 0 : Integer.valueOf(str);
	}

	private static double parseDouble(String str) {
		return str.isBlank() ? 0.0 : Double.valueOf(str);
	}
}
