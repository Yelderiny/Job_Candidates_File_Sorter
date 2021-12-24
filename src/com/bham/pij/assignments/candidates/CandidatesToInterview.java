package com.bham.pij.assignments.candidates;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CandidatesToInterview
{
    private static final String[] keywordsDegree = {"Degree in Computer Science", "Masters in Computer Science"};
    private static final String[] keywordsExperience = {"Data Analyst", "Programmer", "Computer Programmer", "Operator"};
    private final ArrayList<Applicant> qualifiedCandidates = new ArrayList<>();

    //extracts candidates from a file and then shortlists them based on their qualifications
    public void findCandidates()
    {
        //get the input path
        Path input = Path.of("cleancv.txt");
        Path output = Path.of("to-interview.txt");

        //initiate a try-with-resources pattern. one for a stream and one for a writer
        try (Stream<String> lines = Files.lines(input);
             PrintWriter writer = new PrintWriter(
                     new BufferedWriter(
                             new OutputStreamWriter(Files.newOutputStream(output)))))
        {
            /* qualifiedCandidates is built by manipulating the stream to transform lines into Applicant instances
             * then filtering out the unqualified candidates and adding the remaining to the arraylist             */
            qualifiedCandidates.addAll(lines
                    .flatMap(this::lineToApplicant)
                    .filter(this::isQualified)
                    .collect(Collectors.toList()));

            qualifiedCandidates.forEach(candidate -> writer.println(candidate.toString().replaceAll(","," "))); //write the applicants in the output file

        } catch (IOException ioe) { ioe.printStackTrace(); }
    }

    //transforms a line into an applicant stream regardless of whether or not its a txt or a csv
    private Stream<Applicant> lineToApplicant(final String line)
    {
        try
        {
            String[] elements = line.split(","); //split the line by the commas
            Applicant candidate = new Applicant(); //create new applicant instance

            //assign applicant attributes
            candidate.setName(elements[0]);
            candidate.setQualification(elements[1]);
            candidate.seteMail(elements[elements.length - 1]);

            for (int x = 2; x < elements.length - 1; x++)
            {
                if (!elements[x].equals(""))
                {
                    if (x % 2 == 0) candidate.addPosition(elements[x]);
                    else candidate.addYearsOfExperience(Integer.parseInt(elements[x]));
                }
            }
            return Stream.of(candidate); //return the candidate as a stream

        } catch (Exception e) { e.printStackTrace(); }

        return Stream.empty();
    }

    //checks if a candidate is qualified by cross referencing the two data fields of this class
    private boolean isQualified(final Applicant candidate)
    {
        //the candidate has one of the qualifications in the keywordsDegree array
        if(candidate.getQualification().equals(keywordsDegree[0]) || candidate.getQualification().equals(keywordsDegree[1]))
        {
            //iterate through the keywordsExperience array
            for (String experience : keywordsExperience) if (candidate.getPositions().contains(experience)) return true; //the candidate has relevant experience and is qualified
        }
        return false;
    }

    //determines a candidate-with-exeperience based on >5 years of experience in current role and prints it to a txt file
    public void candidatesWithExperience()
    {
        Path output = Path.of("to-interview-experience.txt"); //set output path

        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(Files.newOutputStream(output)))))
        {
            //filter out candidates below five years of experience in their current role and print to output file
            qualifiedCandidates.stream()
                    .filter(candidate -> candidate.getYearsOfExperience().get(0) > 5)
                    .forEach(candidate -> writer.println(candidate.toStringWexperience()));

        } catch (IOException ioe) { ioe.printStackTrace(); }
    }

    //creates a CSV file from the qualified candidates extracted by the findCandidates()
    public void createCSVFile()
    {
        Path output = Path.of("to-interview-table-format.csv"); //set output path

        try(PrintWriter writer = new PrintWriter((
                new BufferedWriter(
                        new OutputStreamWriter(Files.newOutputStream(output))))))
        {
            writer.println("Identifier,Qualification,Position1,Experience1,Position2,Experience2,eMail"); //construct header
            qualifiedCandidates.forEach(candidate -> writer.println(candidate.toCSV())); //print candidates to CSVfile

        } catch (IOException e) { e.printStackTrace(); }
    }

    //prints out the contents of a CSV file of applicants to the console
    public void createReport()
    {
        Path input = Path.of("to-interview-table-format.csv"); //extract input path

        try (Stream<String> lines = Files.lines(input))
        {
            //extract applicants from the CSV file filter out the header and add to an arraylist
            ArrayList<Applicant> forReport = lines.filter(line -> !line.startsWith("Identifier"))
                    .flatMap(this::lineToApplicant)
                    .collect(Collectors.toCollection(ArrayList::new));

            //design table
            System.out.println("-----------------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-31s %-14s %-14s %-5s\n", "Identifier", "Qualification", "Position", "Experience", "eMail");
            System.out.println("-----------------------------------------------------------------------------------------------");
            forReport.forEach(candidate -> System.out.printf("%-15s %-31s %-18s %-10s %-5s\n", candidate.getName(), candidate.getQualification(), candidate.getPositions().get(0), candidate.getYearsOfExperience().get(0), candidate.geteMail()));
            System.out.println("-----------------------------------------------------------------------------------------------");

        } catch (IOException ioe) { ioe.printStackTrace(); }
    }
}
