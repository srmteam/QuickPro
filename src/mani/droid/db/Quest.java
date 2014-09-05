package mani.droid.db;

public class Quest {

	int quesno;
	String ques;
	String[] options = new String[4];
	int ans;
	
	public int getQuesno() {
		return quesno;
	}
	public void setQuesno(int quesno) {
		this.quesno = quesno;
	}
	public String getQues() {
		return ques;
	}
	public void setQues(String ques) {
		this.ques = ques;
	}
	
	public String[] getOptions() {
		return options;
	}
	
	public void setOptions(String[] options) {
		this.options = options;
	}
	public int getAns() {
		return ans;
	}
	public void setAns(int ans) {
		this.ans = ans;
	}
}
