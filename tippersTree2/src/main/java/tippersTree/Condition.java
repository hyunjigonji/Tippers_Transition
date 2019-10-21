package tippersTree;

import java.util.*;

public class Condition {
	public ArrayList<String> conds = new ArrayList<String>(Arrays.asList("Occupancy","Capacity","Location","Connectivity","Temperature")); // contains condition property
	
	public ArrayList<String> Property = OntologyManager.getCondObs();
	
	public boolean test = calculCond("((Temperture+Connectivity)/2>10 || Occupancy<2*Capacity) && Temperture>40");
	
	public boolean calculCond(String input){ 
		System.out.println("START : " + input);
		// 프로퍼티 모두 숫자로 변환 
		for(int i = 0 ; i < Property.size() ; i++) { 
			//System.out.println("now prop = " + Property.get(i));
			if(input.contains(Property.get(i))) {
				String nowProp = Property.get(i);
				int nowNum = extractReal(nowProp);
				input = input.replaceAll(nowProp, Integer.toString(nowNum));
			}
		}
		System.out.println("STEP 1 : " + input);
		
		// AND, OR, 부등호로 파싱해서 사칙연산하기 
		StringTokenizer token = new StringTokenizer(input, "><=!&|");
		while(token.hasMoreTokens()) {
			String now = token.nextToken();
			//System.out.println("nownownowno " + now);
			String nowExp = processing(now); // 띄어쓰기나 괄호 있으면 없애기 
			//System.out.println("STEP 2 : " + nowExp);
			
			int nowResult = calculator(nowExp); // 사칙연산 계산하기 
			input = input.replace(nowExp, Integer.toString(nowResult));
			//System.out.println(nowExp);
		}
		System.out.println("STEP 2 : " + input);
		
		// AND, OR 로 파싱해서 부등호 계산하기 
		StringTokenizer token2 = new StringTokenizer(input, "&|");
		while(token2.hasMoreTokens()) {
			String now2 = token2.nextToken();
			
			String nowExp2 = processing(now2);
			//System.out.println("STEP 4 : " + nowExp2);
			
			String nowResult2 = calculator3(nowExp2);
			input = input.replace(nowExp2, nowResult2);
			//System.out.println(nowExp2);
		}
		System.out.println("STEP 3 : " + input);
		
		boolean result = calculator2(input);
		
		return result;
	}
	
	public String processing(String input) {
		if(input.contains(" ")) input = input.replaceAll(" ", "");
		
		int brackets = 0;
		for(int i = 0 ; i < input.length() ; i++) {
			if(input.charAt(i) == '(') brackets++;
			else if(input.charAt(i) == ')') brackets--;
		}
		if(brackets == 0) return input;
		else if(brackets < 0) { // )가 더 많음 
			brackets = Math.abs(brackets);
			for(int i = 0 ; i < brackets ; i++) {
				for(int j = input.length()-1 ; j >= 0 ; j--) {
					if(input.charAt(j) == ')') {
						input = input.replace(Character.toString(input.charAt(j)),"");
						continue;
					}
				}
			}
		}
		else {
			for(int i = 0 ; i < brackets ; i++) {
				input = input.replaceFirst("[(]", "");
			}
		}
		//System.out.println("processing after = " + input);
		return input;
	}
	
	public ArrayList<String> makePostfix(String input){
		Stack<Character> stack = new Stack<Character>();
		ArrayList<String >newExp = new ArrayList<String>();
		
		String num = "";
		for(int i = 0 ; i < input.length() ; i++) {
			char nowChar = input.charAt(i);
			if (nowChar == ' ') continue;
			
			else if(Character.isDigit(nowChar) || nowChar == '.') { // 숫자가 여러자리 수일 수 있으니까 저장 
				num += nowChar;
			}
			
			else {
				if(!num.isEmpty()) { // 숫자 어레이에 넣기 
					newExp.add(num);
					num = "";
				}
				
				if(nowChar == '(') { // 그냥 스택에 푸쉬 
					stack.push(nowChar);
				}
				else if(nowChar == ')') { // (를 만날 때까지 출력 
					char stackChar = stack.pop();
					while(stackChar != '(' && !stack.isEmpty()) {
						newExp.add(Character.toString(stackChar));
						stackChar = stack.pop();
					}
				}
				else if(nowChar == '+' || nowChar == '-') {
					if(stack.isEmpty()) {
						stack.push(nowChar);
					}
					else {
						while(!stack.isEmpty()) {
							char stackChar = stack.pop();
						
							if(stackChar == '(') {
								stack.push(stackChar);
								stack.push(nowChar);
								break;
							}
							else {
								newExp.add(Character.toString(stackChar));
							}
						}
					}
				}
				else if(nowChar == '/' || nowChar == '*') {
					if(stack.isEmpty()) {
						stack.push(nowChar);
					}
					else {
						while(!stack.isEmpty()) {
							char stackChar = stack.pop();
						
							if(stackChar == '(') {
								stack.push(stackChar);
								stack.push(nowChar);
								break;
							}
							else if(stackChar == '*' || stackChar == '/') {
								newExp.add(Character.toString(stackChar));
							}
							else {
								stack.push(nowChar);
								break;
							}
						}
					}
				}
			}
		}
		if(!num.isEmpty()) { // 숫자 어레이에 넣기 
			newExp.add(num);
			num = "";
		}
		while(!stack.empty()) newExp.add(Character.toString(stack.pop()));
		//System.out.println(newExp);
		
		return newExp;
	}
	
	public int calculator(String input) { // 괄호, 사칙연산 처리하는 계산기 + 만약 사칙연산이 없으면 값을 그대로 리
		// 후위표기법으로 변환 
		ArrayList<String> postfix = makePostfix(input);
		int i = 0;
		while(postfix.size() > 2) {
			String left = postfix.get(i);
			String right = postfix.get(i+1);
			String oper = postfix.get(i+2);
				
			if(oper.equals("+") || oper.equals("-") || oper.equals("*") || oper.equals("/")) {
				int leftNum = Integer.parseInt(left);
				int rightNum = Integer.parseInt(right);
					
				int resultNum = 0;
				if(oper.equals("+")) resultNum = leftNum + rightNum;
				else if(oper.equals("-")) resultNum = leftNum - rightNum;
				else if(oper.equals("*")) resultNum = leftNum * rightNum;
				else if(oper.equals("/")) resultNum = leftNum / rightNum;
					
				postfix.remove(i);
				postfix.remove(i);
				postfix.remove(i);
	
				postfix.add(i, Integer.toString(resultNum));
				
				i = 0;
			}
			else {
				i++;
			}
		}
		int result = Integer.parseInt(postfix.get(0));
		// System.out.println("result === " + result);
		return result;
	}
	
	public ArrayList<String> makePostfix2(String input) {
		Stack<Character> stack = new Stack<Character>();
		ArrayList<String> newExp = new ArrayList<String>();
		
		String bool = "";
		for(int i = 0 ; i < input.length() ; i++) {
			char nowChar = input.charAt(i);
			if(nowChar == ' ') continue;
			
			else if(Character.isAlphabetic(nowChar)) {
				bool += nowChar;
			}
			
			else {
				if(!bool.isEmpty()) { // 숫자 어레이에 넣기 
					newExp.add(bool);
					bool = "";
				}
				
				if(nowChar == '(') { // 그냥 스택에 푸쉬 
					stack.push(nowChar);
				}
				else if(nowChar == ')') { // (를 만날 때까지 출력 
					char stackChar = stack.pop();
					while(stackChar != '(' && !stack.isEmpty()) {
						newExp.add(Character.toString(stackChar));
						stackChar = stack.pop();
					}
				}
				else if(nowChar == '&' || nowChar == '|') {
					if(stack.isEmpty()) {
						stack.push(nowChar);
					}
					else {
						while(!stack.isEmpty()) {
							char stackChar = stack.pop();
						
							if(stackChar == '(') {
								stack.push(stackChar);
								stack.push(nowChar);
								break;
							}
							else {
								newExp.add(Character.toString(stackChar));
							}
						}
					}
					i++;
				}
			}
		}
		if(!bool.isEmpty()) { // 숫자 어레이에 넣기 
			newExp.add(bool);
			bool = "";
		}
		while(!stack.empty()) newExp.add(Character.toString(stack.pop()));
		//System.out.println(newExp);
		
		return newExp;
	}
	
	public Boolean calculator2(String input) { // 부등호 계산 
		ArrayList<String> postfix = makePostfix2(input);
		int i = 0;
		while(postfix.size() > 2) {
			String left = postfix.get(i);
			String right = postfix.get(i+1);
			String oper = postfix.get(i+2);
				
			if(oper.equals("&") || oper.equals("|")) {
				String result = "FALSE";
				
				if(oper.equals("&")) {
					if(left.equals("TRUE") == true && right.equals("TRUE")) result = "TRUE";
				}
				if(oper.equals("|")) {
					if(left.equals("TRUE") || right.equals("TRUE")) result = "TRUE";
				}
				
				postfix.remove(i);
				postfix.remove(i);
				postfix.remove(i);
	
				postfix.add(i,result);
				
				i = 0;
			}
			else {
				i++;
			}
		}
		String result = postfix.get(0);
		System.out.println("STEP 4 : " + result);
		
		boolean resultBool = false;
		if(result.equals("TRUE")) resultBool = true;
		
		return resultBool;
	}
	
	public String calculator3(String input) {
		int ind = 0;
		String result = "FALSE";
		if(input.contains(">")) {
			ind = input.indexOf(">");
			
			int left = Integer.parseInt(input.substring(0,ind-1));
			int right = Integer.parseInt(input.substring(ind+1, input.length()-1));
			
			if(left > right) result = "TRUE";
		}
		else if(input.contains(">=")) {
			ind = input.indexOf(">=");
			
			int left = Integer.parseInt(input.substring(0,ind-1));
			int right = Integer.parseInt(input.substring(ind+2, input.length()-1));
			
			if(left >= right) result = "TRUE";
		}
		else if(input.contains("<")) {
			ind = input.indexOf("<");
			
			int left = Integer.parseInt(input.substring(0,ind-1));
			int right = Integer.parseInt(input.substring(ind+1, input.length()-1));
			
			if(left < right) result = "TRUE";
		}
		else if(input.contains("<=")) {
			ind = input.indexOf("<=");
			
			int left = Integer.parseInt(input.substring(0,ind-1));
			int right = Integer.parseInt(input.substring(ind+2, input.length()-1));
			
			if(left > right) result = "TRUE";
		}
		
		return result;
	}
	
	public int extractReal(String input) {
		if(input.equals("Location")) return 15;
		else if(input.equals("Occupancy")) return 20;
		else if(input.equals("Capacity")) return 25;
		else if(input.equals("Connectivity")) return 30;
		else if(input.equals("Temperture")) return 35;
		else if(input.equals("Image")) return 40;
		else return 0;
	}
	
}