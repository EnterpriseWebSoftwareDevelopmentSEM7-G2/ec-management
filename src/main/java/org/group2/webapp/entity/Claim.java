/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.group2.webapp.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.group2.webapp.util.ConvertUntil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "claim")
public class Claim implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT", unique = true, nullable = true)
	private String evidence;

	@NotNull
	@Column(columnDefinition = "TEXT", unique = true, nullable = true)
	private String content;

	@Column(columnDefinition = "TEXT", nullable = true)
	private String decision;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time", nullable = false)
	private Date created_time = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "processed_time")
	private Date processed_time;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "closed_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date closedDate;

	@Column(name = "status")
	private int status = 0;

	@Column(name = "seen")
	private Boolean seen =false;

	@Column(name = "changed_times")
	private Integer changedTimes = 0;

	@Column(name = "over_dateline_process")
	private Boolean overDatelineProcess = false;

	@Column(name = "can_upload_more_evidence")
	private Boolean canUploadMoreEvidence = true;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="deadline_upload_evidence")
//	private Date deadlineUploadEvidence;
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="deadline_process")
//	private Date deadlineProcess;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_remind_process")
	private Date lastDateRemind;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_remind_upload_evidence")
	private Date lastRemindUploadDate;

	@ManyToOne
	private User user;

	@ManyToOne
	private Item item;

	@JsonIgnore
	@OneToMany(mappedBy = "claim")
	private Set<Evidence> evidences = new HashSet<>();

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "claim_circumstance", joinColumns = {
			@JoinColumn(name = "claim_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "circumstance_id", referencedColumnName = "id") })
	private Set<Circumstance> circumstances = new HashSet<>();

	public Claim() {
	}

	public Claim(Long id, String evidence, String content, String decision, Date created_time, Date processed_time,
			Date closedDate, int status) {
		this.id = id;
		this.evidence = evidence;
		this.content = content;
		this.decision = decision;
		this.created_time = created_time;
		this.processed_time = processed_time;
		this.closedDate = closedDate;
		this.status = status;
	}

	public Claim(String evidence, String content, Date created_time, Date processed_time, int status, User user,
			Date closedDate) {
		this.evidence = evidence;
		this.content = content;
		this.created_time = created_time;
		this.processed_time = processed_time;
		this.closedDate = closedDate;
		this.status = status;
		this.user = user;
	}
	
//	public boolean isOverDeadlineUploadEvidence(){
//		Calendar cal=Calendar.getInstance();
//		cal.setTime(deadlineUploadEvidence);
//		cal.add(Calendar.DAY_OF_MONTH, SO_NGAY_NHAC_NHO_UPLOAD_EVIDENCE);
//		return 
//	}


	public void addCircumstance(Circumstance circumstance) {
		this.circumstances.add(circumstance);
	}

	public void clearCircumstances() {
		this.circumstances.clear();
	}

	public Set<Circumstance> getCircumstances() {
		return circumstances;
	}

	public void setCircumstances(Set<Circumstance> circumstances) {
		this.circumstances = circumstances;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEvidence() {
		return evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

	public Date getProcessed_time() {
		return processed_time;
	}

	public void setProcessed_time(Date processed_time) {
		this.processed_time = processed_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public boolean isMissClosedDate() {
		if (this.closedDate == null)
			return false;
		if (this.closedDate.getTime() > this.created_time.getTime())
			return false;
		return true;
	}

	public boolean isLackOfEvidence() {
		if (evidence == null)
			return true;
		return false;
	}

	public boolean isValid() {
		if (isMissClosedDate())
			return false;
		if (evidence == null)
			return false;
		return true;
	}

	public boolean isLateProcessed() {
		if (!isProcessed())
			return false;
		if (this.processed_time.getTime() > ConvertUntil.increase14Day(this.created_time).getTime())
			return true;
		return false;
	}

	public boolean isProcessed() {
		return this.processed_time != null;
	}


	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Boolean getSeen() {
		return seen;
	}

	public void setSeen(Boolean seen) {
		this.seen = seen;
	}


	public Set<Evidence> getEvidences() {
		return evidences;
	}

	public void setEvidences(Set<Evidence> evidences) {
		this.evidences = evidences;
	}

	public Date getLastDateRemind() {
		return lastDateRemind;
	}

	public void setLastDateRemind(Date lastDateRemind) {
		this.lastDateRemind = lastDateRemind;
	}

	public int getChangedTimes() {
		return changedTimes;
	}

	public void setChangedTimes(int changedTimes) {
		this.changedTimes = changedTimes;
	}

	public Boolean getOverDatelineProcess() {
		return overDatelineProcess;
	}

	public void setOverDatelineProcess(Boolean overDatelineProcess) {
		this.overDatelineProcess = overDatelineProcess;
	}

	public Boolean getCanUploadMoreEvidence() {
		return canUploadMoreEvidence;
	}

	public void setCanUploadMoreEvidence(Boolean canUploadMoreEvidence) {
		this.canUploadMoreEvidence = canUploadMoreEvidence;
	}

	public void setChangedTimes(Integer changedTimes) {
		this.changedTimes = changedTimes;
	}

	public Date getLastRemindUploadDate() {
		return lastRemindUploadDate;
	}

	public void setLastRemindUploadDate(Date lastRemindUploadDate) {
		this.lastRemindUploadDate = lastRemindUploadDate;
	}

	@Override
	public String toString() {
		return "Claim [id=" + id + ", evidence=" + evidence + ", content=" + content + ", decision=" + decision
				+ ", created_time=" + created_time + ", processed_time=" + processed_time + ", closedDate=" + closedDate
				+ ", status=" + status + ", seen=" + seen + ", changedTimes=" + changedTimes + ", overDatelineProcess="
				+ overDatelineProcess + ", canUploadMoreEvidence=" + canUploadMoreEvidence + ", lastDateRemind="
				+ lastDateRemind + ", lastRemindUploadDate=" + lastRemindUploadDate + ", user=" + user + ", item="
				;
	}
}
