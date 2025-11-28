package helpers;

import java.io.File;

import static io.qameta.allure.Allure.step;

public class ScriptHelper {
    public static void execute(String scriptName) {
        step("Run script with name: " + scriptName, ()-> {
            ProcessBuilder processBuilder = new ProcessBuilder("./" + scriptName);
            processBuilder.directory(new File(System.getProperty("user.dir")));

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("Failed to execute script with name: " + scriptName
                        + ", exit code: " + exitCode);
            } else {
                System.out.println("✅ Script with name " + scriptName + " executed successfully!");
            }
        });
    }

    public static void putEnvVariable(String name, String value) {
        step("Set env variable: " + name + "=" + value, () -> {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.environment().put(name, value);
        });
    }
}
