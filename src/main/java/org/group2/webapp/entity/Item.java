
package org.group2.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author dfChicken
 */
@Entity
@Table(name = "item")
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 0, max = 50)
	@Id
	@Column(length = 50, unique = true, nullable = false)
	private String crn;

	@NotNull
	@Size(min = 1, max = 100)
	@Column(length = 100, unique = true, nullable = false)
	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "closed_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date closedDate;

	@ManyToOne
	private Assessment assessment;

	@JsonIgnore
	@OneToMany(mappedBy = "item")
	private Set<Claim> claim = new HashSet<>();

	public Item() {
	}

	public Set<Claim> getClaim() {
		return claim;
	}

	public void setClaim(Set<Claim> claim) {
		this.claim = claim;
	}

	public Item(String crn, Assessment course, String title) {
		super();
		this.crn = crn;
		this.title = title;
		this.assessment = course;
	}

	public boolean isMissClosedDate() {
		if (this.closedDate == null)
			return false;
		if (this.closedDate.getTime() > Calendar.getInstance().getTime().getTime())
			return false;
		return true;
	}

	public boolean isValid() {
		return !isMissClosedDate();
	}

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setCourse(Assessment course) {
		this.assessment = course;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	@Override
	public String toString() {
		return "Item [crn=" + crn + ", title=" + title + ", closedDate=" + closedDate + ", assessment=" + assessment
				+ ", claim=" + claim + "]";
	}

}
