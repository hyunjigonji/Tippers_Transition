package tippersTree;

import java.util.*;

public class Condition {
	public static ArrayList<String> Property = OntologyManager.getCondObs();
	
	public static boolean test = calculCond("Temperature == 40 || Occupancy * 0.4 < 20 && Capacity / 2 > 50 || Image + Occupancy > 20");
	
	public static boolean calculCond(String input){ 
		System.out.println("Calculating Condition : " + input);
		// 프로퍼티 모두 숫자로 변환 
		for(int i = 0 ; i < Property.size() ; i++) { 
			//System.out.println("now prop = " + Property.get(i));
			if(input.contains(Property.get(i))) {
				String nowProp = Property.get(i);
				float nowNum = extractReal(nowProp);
				input = input.replaceAll(nowProp, Float.toString(nowNum));
			}
		}
		System.out.println("STEP 1 : " + input);
		
		// AND, OR, 부등호로 파싱해서 사칙연산하기 
		String input2 = "";
		StringTokenizer token = new StringTokenizer(input, "><=!&|", true);
		while(token.hasMoreTokens()) {
			String now = token.nextToken();
			//System.out.println(now);
			if(now.equals(">") || now.equals("<") || now.equals("=") || now.equals("!")|| now.equals("&") || now.equals("|")) {
				input2 += now;
			}
			else {
				String nowResult = calculator(now); // calculate arithmetic expression
				//System.out.println(nowResult);
				input2 += nowResult;
			}
		}
		input = input2;
		System.out.println("STEP 2 : " + input);
		
		// AND, OR 로 파싱해서 부등호 계산하기 
		String input3 = "";
		StringTokenizer token2 = new StringTokenizer(input, "&|", true);
		while(token2.hasMoreTokens()) {
			String now2 = token2.nextToken();
			//System.out.println("now2 " + now2);
			if(now2.equals("&") || now2.equals("|")) {
				input3 += now2;
			}
			else {
				String nowResult2 = calculator2(now2);
				input3 += nowResult2;
			}
		}
		input = input3;
		System.out.println("STEP 3 : " + input);
		
		// AND, OR 계산하기 
		boolean result = calculator3(input);
		System.out.println("result : " + result);
		System.out.println();
		
		return result;
	}
	
	public static String processing(String input) {
		//System.out.println("processing input : " +input);
		if(input.contains(" ")) input = input.replaceAll(" ", "");
		
		int brackets = 0;
		for(int i = 0 ; i < input.length() ; i++) {
			if(input.charAt(i) == '(') brackets++;
			else if(input.charAt(i) == ')') brackets--;
		}
		if(brackets < 0) { // ) > (
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
		else if(brackets > 0){ // ( > )
			for(int i = 0 ; i < brackets ; i++) {
				input = input.replaceFirst("[(]", "");
			}
		}
		//System.out.println("processing after = " + input);
		return input;
	}
	
	public static ArrayList<String> makePostfix(String input){ // makes calculating expression to postfix expression
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
	
	public static String calculator(String input) { // calculates expression which contains only numbers and operators
		//System.out.println("calculator input : " + input);
		String newInput = processing(input); // remove brackets or blank
		//System.out.println(newInput);
		ArrayList<String> postfix = makePostfix(newInput); // get array in post order
		//System.out.println(postfix);
		int i = 0;
		while(postfix.size() > 2) {
			String left = postfix.get(i);
			String right = postfix.get(i+1);
			String oper = postfix.get(i+2);
				
			if(oper.equals("+") || oper.equals("-") || oper.equals("*") || oper.equals("/")) {
				float leftNum = Float.parseFloat(left);
				float rightNum = Float.parseFloat(right);
					
				float resultNum = 0;
				if(oper.equals("+")) resultNum = leftNum + rightNum;
				else if(oper.equals("-")) resultNum = leftNum - rightNum;
				else if(oper.equals("*")) resultNum = leftNum * rightNum;
				else if(oper.equals("/")) resultNum = leftNum / rightNum;
				//System.out.println(resultNum);
				postfix.remove(i);
				postfix.remove(i);
				postfix.remove(i);
	
				postfix.add(i, Float.toString(resultNum)); // put result into array and remove just calculated elements
				
				i = 0;
			}
			else { // if third one is not an operator, continue
				i++;
			}
		}
		float result = Float.parseFloat(postfix.get(0));
		//System.out.println("result === " + result);
		input = input.replace(newInput, Float.toString(result));
		//System.out.println(input);
		return Float.toString(result);
	}
	
	public static ArrayList<String> makePostfix2(String input) { // makes boolean expression to postfix expression
		Stack<Character> stack = new Stack<Character>(); // contains operator
		ArrayList<String> newExp = new ArrayList<String>(); // contains all elements in post order

		String bool = "";
		for(int i = 0 ; i < input.length() ; i++) {
			char nowChar = input.charAt(i);
			if(nowChar == ' ') continue; // ignore blank
			
			else if(Character.isAlphabetic(nowChar)) {
				bool += nowChar; // collects alphabet
			}
			
			else {
				if(!bool.isEmpty()) { // put alphabets into array
					newExp.add(bool);
					bool = "";
				}
				
				if(nowChar == '(') { // just push to stack
					stack.push(nowChar);
				}
				else if(nowChar == ')') { // print until meeting (
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
								//stack.push(nowChar);
								break;
							}
							else {
								newExp.add(Character.toString(stackChar));
							}
						}
						stack.push(nowChar);
					}
					i++;
				}
			}
		}
		if(!bool.isEmpty()) { // put boolean values into array
			newExp.add(bool);
			bool = "";
		}
		while(!stack.empty()) newExp.add(Character.toString(stack.pop()));
		//System.out.println(newExp);
		
		return newExp;
	}
	
	public static String calculator2(String input) { // calculates expression which contains only > or <
		String newInput = processing(input);
		int ind = 0;
		String result = "FALSE";
		if(newInput.contains(">") && !newInput.contains("=")) {
			ind = newInput.indexOf(">");
			
			float left = Float.parseFloat(newInput.substring(0,ind-1));
			float right = Float.parseFloat(newInput.substring(ind+1, newInput.length()-1));
			
			if(left > right) result = "TRUE";
		}
		else if(newInput.contains(">=")) {
			ind = newInput.indexOf(">=");
			
			float left = Float.parseFloat(newInput.substring(0,ind-1));
			float right = Float.parseFloat(newInput.substring(ind+2, newInput.length()-1));
			
			if(left >= right) result = "TRUE";
		}
		else if(newInput.contains("<") && !newInput.contains("=")) {
			ind = newInput.indexOf("<");
			
			float left = Float.parseFloat(newInput.substring(0,ind-1));
			float right = Float.parseFloat(newInput.substring(ind+1, newInput.length()-1));
			
			if(left < right) result = "TRUE";
		}
		else if(newInput.contains("<=")) {
			ind = newInput.indexOf("<=");
			
			float left = Float.parseFloat(newInput.substring(0,ind-1));
			float right = Float.parseFloat(newInput.substring(ind+2, newInput.length()-1));
			
			if(left <= right) result = "TRUE";
		}
		else if(newInput.contains("!=")) {
			ind = newInput.indexOf("!=");
			
			float left = Float.parseFloat(newInput.substring(0,ind-1));
			float right = Float.parseFloat(newInput.substring(ind+2, newInput.length()-1));
			
			if(left != right) result = "TRUE";
		}
		else if(newInput.contains("==")) {
			ind = newInput.indexOf("==");
			
			float left = Float.parseFloat(newInput.substring(0,ind-1));
			float right = Float.parseFloat(newInput.substring(ind+2, newInput.length()-1));
			
			if(left == right) result = "TRUE";
		}
		input = input.replace(newInput, result);
		
		return input;
	}
	
	public static Boolean calculator3(String input) { // calculate expression which contains boolean values
		//String newInput = processing(input);
		ArrayList<String> postfix = makePostfix2(input); // get array in post order 
		//System.out.println(postfix);
		int i = 0;
		while(postfix.size() > 2) {
			String left = postfix.get(i);
			String right = postfix.get(i+1);
			String oper = postfix.get(i+2);
				
			if(oper.equals("&") || oper.equals("|")) {
				String result = "";
				
				if(oper.equals("&")) {
					if(left.equals("TRUE") && right.equals("TRUE")) result = "TRUE";
					else result = "FALSE";
				}
				if(oper.equals("|")) {
					if(left.equals("FALSE") || right.equals("FALSE")) result = "FALSE";
					else result = "TRUE";
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
	
	public static float extractReal(String input) { // temporarily get sensor value
		if(input.equals("Location")) return (float)15.6;
		else if(input.equals("Occupancy")) return (float)20.4;
		else if(input.equals("Capacity")) return (float)0.8;
		else if(input.equals("Connectivity")) return (float)30.9;
		else if(input.equals("Temperature")) return (float)35;
		else if(input.equals("Image")) return (float)40.5;
		
		else return 0;
	}
	
}