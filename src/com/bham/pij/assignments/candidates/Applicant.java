package com.bham.pij.assignments.candidates;

import java.util.ArrayList;

public class Applicant
{
    //data fields
    private String name;
    private String qualification;
    private final ArrayList<String> positions = new ArrayList<>();
    private final ArrayList<Integer> yearsOfExperience = new ArrayList<>();
    private String eMail;

    //accessors
    public String getName() { return name; }
    public String getQualification() { return qualification; }
    public String geteMail() { return eMail; }
    public ArrayList<String> getPositions() { return positions; }
    public ArrayList<Integer> getYearsOfExperience() { return yearsOfExperience; }

    //mutators
    public void setName(String name) { this.name = name; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public void seteMail(String eMail) { this.eMail = eMail; }
    public void addPosition(String position) { positions.add(position); }
    public void addYearsOfExperience(Integer experience) { yearsOfExperience.add(experience); }

    @Override
    public String toString()
    {
        String appInfo = name + "," + qualification + ",";

        //concatenate all available positions and experience years
        for (int x = 0; x < positions.toArray().length; x++)
        {
            appInfo = appInfo.concat(positions.get(x) + "," + yearsOfExperience.get(x) + ",");
        }
        appInfo = appInfo.concat(eMail);

        return appInfo;
    }

    public String toCSV()
    {
        try
        {
            return name + "," + qualification + "," + positions.get(0) + "," + yearsOfExperience.get(0) + "," + positions.get(1) + "," + yearsOfExperience.get(1) + "," + eMail;

        } catch (IndexOutOfBoundsException e) { return name + "," + qualification + "," + positions.get(0) + "," + yearsOfExperience.get(0) + ",,," + eMail; }
    }
    public String toStringWexperience() { return name + " " + yearsOfExperience.get(0); }


}
