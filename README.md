```



<?xml version="1.0" encoding="UTF-8"?>
<person>
	<human>
    		<name>RAHUL</name>
	</human>
    <age>30</age>
    <occupation>Engineer</occupation>
</person>


```


```

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppController {

    @Autowired
    AppService appService;

    @GetMapping("/api/identifyFiles/{name}")
    public void identifyFiles(@PathVariable String name) {
        appService.identifyFiles(name);
    }
}

```


```
package com.example.RBCMLScanner;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Service
public class AppService {

    public void identifyFiles(String name){
        // Source and destination folder paths
        String sourceFolderPath = "/Users/rahulvaish/JunkExperiments"; // Replace with your source folder
        String destinationFolderPath = "/Users/rahulvaish/JunkExperiments/destination"; // Replace with your destination folder

        // Create destination folder if it doesn't exist
        File destinationFolder = new File(destinationFolderPath);
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        try {
            // Define the target date and size criteria
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            Date targetDate = dateFormat.parse("19 Sep 2024");
            long sizeThreshold = 130L; // 5MB


            // Walk through the files in the source directory
            Files.walkFileTree(Paths.get(sourceFolderPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // Check if file was created on the target date and has size > 5MB
                    if (isTargetDate(attrs, targetDate) && attrs.size() > sizeThreshold) {
                        // Check if it's an XML file and contains the required content
                        if (isXmlFile(file) && containsNameTag(file, name)) {
                            // Move the file to the destination folder
                            moveFileToDestination(file, destinationFolderPath);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Check if file creation date matches target date
    private static boolean isTargetDate(BasicFileAttributes attrs, Date targetDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String fileCreationDate = dateFormat.format(new Date(attrs.creationTime().toMillis()));
        String targetDateStr = dateFormat.format(targetDate);
        return fileCreationDate.equals(targetDateStr);
    }

    // Check if the file is an XML file by checking its extension
    private static boolean isXmlFile(Path file) {
        return file.toString().toLowerCase().endsWith(".xml");
    }

    // Check if the file contains the <name>RAHUL</name> tag
    private static boolean containsNameTag(Path file, String name) {
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("<name>" + name + "</name>")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Move the file to the destination folder
    private static void moveFileToDestination(Path file, String destinationFolderPath) {
        try {
            Path destinationPath = Paths.get(destinationFolderPath, file.getFileName().toString());
            Files.move(file, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved file: " + file.getFileName() + " to " + destinationFolderPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


```
