/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author amandeepsingh12
 */
public class ComputedFeedbackDataModel {
    private int feedback_id, total_members,validated_members;
    private String applicant_name,committee_name,designation_name,applicant_description;

    public int getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
    }

    public int getTotal_members() {
        return total_members;
    }

    public void setTotal_members(int total_members) {
        this.total_members = total_members;
    }

    public int getValidated_members() {
        return validated_members;
    }

    public void setValidated_members(int validated_members) {
        this.validated_members = validated_members;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public String getCommittee_name() {
        return committee_name;
    }

    public void setCommittee_name(String committee_name) {
        this.committee_name = committee_name;
    }

    public String getDesignation_name() {
        return designation_name;
    }

    public void setDesignation_name(String designation_name) {
        this.designation_name = designation_name;
    }

    public String getApplicant_description() {
        return applicant_description;
    }

    public void setApplicant_description(String applicant_description) {
        this.applicant_description = applicant_description;
    }
    
    
}
