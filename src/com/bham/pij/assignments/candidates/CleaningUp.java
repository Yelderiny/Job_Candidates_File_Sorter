package com.bham.pij.assignments.candidates;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class CleaningUp
{

    public void cleanUpFile()
    {
        final int REQUESTED_DATA = 0;
        final int WRITTEN_DATA = 1;

        //get the input path
        Path input = Path.of("dirtycv.txt");
        Path output = Path.of("cleancv.txt");

        //initiate a try-with-resources pattern with both the writer and the reader
        try (Scanner reader = new Scanner(new BufferedReader(new InputStreamReader(Files.newInputStream(input))));
             PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(output)))))
        {
            int counter = 1;
            while(reader.hasNextLine())
            {
                String line = reader.nextLine();

                //the line starts with cv followed by some number
                if (line.split(" ")[0].equalsIgnoreCase("cv"))
                {
                    Applicant candidate = new Applicant(); //create an applicant

                    line = reader.nextLine(); //read the first line in the cv block
                    while (!line.equals("End"))
                    {
                        String[] elements = line.split(":"); //split the elements of the line

                        switch (elements[REQUESTED_DATA])
                        {
                            case "Surname", "surname" -> candidate.setName(elements[WRITTEN_DATA] + "000" + counter++);
                            case "Qualification", "qualification" -> candidate.setQualification(elements[WRITTEN_DATA]);
                            case "Position", "position" -> candidate.addPosition(elements[WRITTEN_DATA]);
                            case "Experience", "experience" -> candidate.addYearsOfExperience(Integer.parseInt(elements[WRITTEN_DATA]));
                            case "eMail", "email" -> candidate.seteMail(elements[WRITTEN_DATA]);
                        }
                        line = reader.nextLine(); //move to the next line
                    }
                    writer.println(candidate + ","); //write to new file
                }
            }
        } catch (IOException ioe) { ioe.printStackTrace(); }
    }
}
