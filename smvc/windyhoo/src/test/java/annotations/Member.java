package annotations;

@DBTable(name="MEMBER")
public class Member {
	@SqlString(30)
	String firstName;
	
	@SqlString(50)
	String lastName;
	
	@SqlInteger
	Integer age;
	
	@SqlString(value=90,constraints=@Constraints(primaryKey=true))
	String handle;
	
	static int memberCount;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public static int getMemberCount() {
		return memberCount;
	}

	public static void setMemberCount(int memberCount) {
		Member.memberCount = memberCount;
	}

	@Override
	public String toString() {
		return handle;
	}
	
}
