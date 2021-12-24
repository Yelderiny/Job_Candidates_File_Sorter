package com.bham.pij.assignments.candidates;

public class JobCandidatesMain
{
    public static void main(String[] args)
    {
        CleaningUp bro = new CleaningUp();

        bro.cleanUpFile();

        CandidatesToInterview bruv = new CandidatesToInterview();

        bruv.findCandidates();
        bruv.candidatesWithExperience();
        bruv.createCSVFile();
        bruv.createReport();
    }
}
