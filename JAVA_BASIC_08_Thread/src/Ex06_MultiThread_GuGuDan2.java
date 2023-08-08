import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

class QuizInfo2 {
	String question;
	int answer;
	String result;

	public QuizInfo2(String question, int answer, String result) {
		this.question = question;
		this.answer = answer;
		this.result = result;
	}

	public String toString() {
		return "문제:" + this.question + " 답:" + this.answer + "결과:" + this.result;
	}
}

class TimeOut extends Thread {
	@Override
	public void run() {
		for (int i = 10; i > 0; i--) {
			try {
				System.out.println("남은 시간 : " + i);
				Thread.sleep(1000); // 휴게실 가서 1초 쉬었다가 다시 와~ >> runnable 상태
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}
		Ex06_MultiThread_GuGuDan2.inputCheck = true; // 시간 종료
		System.out.println("시간 종료 ^^");
	}
}

class WordGame extends Thread {
	int answer;
	@Override
	public void run() {
		HashMap<String, Integer> gugudan = new HashMap<String, Integer>();
		for (int i = 0; i < 10; i++) {
			int num1 = (int) (Math.random() * 9) + 1;
			int num2 = (int) (Math.random() * 9) + 1;
			gugudan.put(num1 + "*" + num2, num1 * num2);
		}
		Ex06_MultiThread_GuGuDan2.totalQuizCount = gugudan.size();
		
		// 초기화
		Set keys = gugudan.keySet();
		Object[] keylist = keys.toArray();
		int answer = 0;
		
		for (int i = 0; i < keylist.length; i++) {
			answer = Integer.parseInt(JOptionPane.showInputDialog((String) keylist[i]));
			if(Ex06_MultiThread_GuGuDan2.inputCheck) break;			
			if (gugudan.get(keylist[i]) == answer) {
				System.out.println("당신은 정답");
				Ex06_MultiThread_GuGuDan2.QuizUserInfo.add(new QuizInfo2((String) keylist[i], answer, "O"));
			} else {
				System.out.println("당신은 오답");
				Ex06_MultiThread_GuGuDan2.QuizUserInfo.add(new QuizInfo2((String) keylist[i], answer, "X"));
			}
		}
	}
}

public class Ex06_MultiThread_GuGuDan2 {
	static boolean inputCheck = false;
	static List<QuizInfo2> QuizUserInfo = new ArrayList<>();
	static int totalQuizCount = 0;
	
	public static void main(String[] args) {
		TimeOut timeOut = new TimeOut();
		WordGame wordGame = new WordGame();
		
		timeOut.start();
		wordGame.start();
		
		try {
			timeOut.join();
			wordGame.join();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		System.out.println("총문제수 : " + totalQuizCount + " 푼 문제수 : " + QuizUserInfo.size());
		for(int i=0; i<QuizUserInfo.size(); i++) {
			System.out.println(QuizUserInfo.get(i).toString());
		}
	}
}
