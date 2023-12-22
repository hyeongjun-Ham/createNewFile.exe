import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class RandomZipCreator {

    public static void main(String[] args) {
        // 바탕화면 경로에 폴더 생성
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        String outputFolderPath = desktopPath + File.separator + "DummyFile";

        // 폴더가 없으면 생성
        File outputFolder = new File(outputFolderPath);
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }

        createRandomZipFile(outputFolderPath);
    }

    private static String generateRandomString(int length) {
        StringBuilder randomString = new StringBuilder(length);
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            randomString.append(characters.charAt(randomIndex));
        }

        return randomString.toString();
    }

    private static String generateRandomHash() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String randomString = generateRandomString(16);
        byte[] hashBytes = md.digest(randomString.getBytes(StandardCharsets.UTF_8));

        // Convert hash bytes to hexadecimal representation
        StringBuilder hashHex = new StringBuilder();
        for (byte hashByte : hashBytes) {
            hashHex.append(String.format("%02x", hashByte));
        }

        return hashHex.toString();
    }

    private static void createRandomZipFile(String outputFolderPath) {
        try {
            // Generate a random hash
            String randomHash = generateRandomHash();

            // Create the output .zip file on the desktop
            String zipFileName = "random_zip_" + randomHash + ".zip";
            String zipFilePath = outputFolderPath + File.separator + zipFileName;

            try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
                // Add sample content to the Zip file
                zipOutputStream.putNextEntry(new ZipEntry("sample.txt"));
                zipOutputStream.write(randomHash.getBytes());
                zipOutputStream.closeEntry();
            }

            System.out.println("Random ZIP file created: " + zipFilePath);

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}