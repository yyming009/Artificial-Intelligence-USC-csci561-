package homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class homework {
	
	static int numOfQuery;
	static int numOfKb;
	static String[] query;
	static String[] kb;
	
	static LinkedList<Map<String, LinkedList<String>>> compareList = new LinkedList<Map<String, LinkedList<String>>>();
	static Map<String,String> substiCheck = new HashMap<String,String>();
	static List<String> print = new LinkedList<String>();

	public static void main(String[] args) {
		
		String pathName = "input.txt";
		File file = new File(pathName);		
		
		Scanner input = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//get the number of queries
		numOfQuery = Integer.parseInt(input.nextLine());
		query = new String[numOfQuery];
		//store each query of each line into query array
		for (int i = 0; i < numOfQuery && input.hasNextLine(); ++i) {
			query[i] = input.nextLine();
			//System.out.println(query[j]);
		}

		//get the number of kb
		numOfKb = Integer.parseInt(input.nextLine());
		kb = new String[numOfKb];
		//store each kb of each line into query array
		for(int j = 0; j < numOfKb && input.hasNextLine(); ++j) { 
		    kb[j] = input.nextLine();
			//System.out.println(kb[j]);
		}

		for (int j = 0; j < numOfKb; ++j) {
			bulidSearchMap(kb[j]);
		}
		
		for(int i = 0; i < numOfQuery; ++i) {
			resolution(query[i]);
		}
	}
	
	private static void bulidSearchMap(String sen_kb) {
		String s = "";
		for (int i = 0; i < sen_kb.length(); ++i) {
			if (sen_kb.charAt(i) != ' ') {
				s += (sen_kb.charAt(i));
			}
		}
		//System.out.println(s);
		
		Map<String, LinkedList<String>> tempMap = new HashMap<String, LinkedList<String>>();
		
		String[] orDivide = s.split("\\|");
		for (int i = 0; i < orDivide.length; ++i) {
			String sep = orDivide[i];
			char[] char_sep = sep.toCharArray();
			LinkedList<String> tempList;
			
			String key="";
			for (char ch : char_sep) {
				if (ch == '(') {
					break;
				}
				key += ch;
			}
			
			if(tempMap.containsKey(key)) {
				tempList = tempMap.get(key);
				tempList.add(sep);
			} else {
				tempList = new LinkedList<String>();
				tempList.add(sep);
				tempMap.put(key,tempList);
			}
		}
		compareList.add(tempMap);
		//System.out.println(compareList);
	}
	
	private static void resolution(String s) {
		if(s.charAt(0)=='~') {
			s = s.substring(1, s.length());
		} else {
			s = '~' + s;
		}
		
		List<String> li = new LinkedList<String>();
		li.add(s);
		
		substiCheck.clear();
		
		int ind=0;
		String sub = substitute(li, ind++);
		
		System.out.println(sub);
		print.add(sub);
	}
	
	private static String substitute(List<String> li, int nums_arg) {
		
//******************************************************handle to query********************************************************		
		for (int i = 0; i < li.size(); ++i) {
			String s = li.get(i);			
			char[] char_query = s.toCharArray();
			
			String constant = "", argument = "";
			
			//get the constant
			int index = 0;
			while (index < char_query.length) {
				if (char_query[index] == '(') {
					index++;
					break;
				}
				constant += char_query[index];
				index++;
			}
			
			//get the argument, split each argument then store it in qryarg_split
			for (int j = index; j < char_query.length; ++j) {
				if (char_query[j] ==  ')') {
					break;
				}
				if (char_query[j] == ' ') {
					continue;
				}
				argument += char_query[j];
			}
			String[] qryarg_split = argument.split("\\,");
			
//******************************************************handle to kb sentences********************************************************		

			//For every query, try to check if it matches every sentence in KB
			outer:
			for(int m = 0; m < compareList.size(); ++m) {
				//get the every line of sentence in kb
				Map<String, LinkedList<String>> tempMap = new HashMap<String, LinkedList<String>>();
				tempMap = compareList.get(m);
				
				//use iterator to traverse the keyset in every hashmap positted in every index of comparaList
				Iterator<Entry<String, LinkedList<String>>> iter = tempMap.entrySet().iterator();
								
				String mapKey="", variables="";
				String mapValue = "";
				String variable = "";
				
				char[] var;
				boolean flag = false;
				
				label:
				while (iter.hasNext()) {
					//get the constant in KB
					Map.Entry pair = (Map.Entry)iter.next();
					mapKey = pair.getKey().toString();
					
					String[] kbvar_split = {};

//					if (constant.equals(mapKey)) {
//						flag = true;
//					}
					
					if(constant.charAt(0)=='~'){
						//System.out.println(predicate.substring(1,predicate.length()));
						if(constant.substring(1, constant.length()).equals(mapKey))
							flag = true;
					}
					else{
						if(mapKey.substring(1, mapKey.length()).equals(constant))
							flag = true;
					}
					
					if(flag) {
						mapValue = pair.getValue().toString();
						char[] char_value = mapValue.toCharArray();
						
						//get the variables of each kb stored in kbvar_split
						for(int k = 0; k < char_value.length; ++k){
							if(char_value[k] == '('){
								++k;
								while(char_value[k] != ')'){
									variable += char_value[k++];	
								}
							}
						}
						kbvar_split = variable.split("\\,");	
					}		
					
					if (kbvar_split.length == qryarg_split.length) {
						
						HashMap<String,String> subMap = new HashMap();
						
						if(tempMap.size() == 1) {

							String[] sub = new String[kbvar_split.length];
							
							for(int ind = 0; ind < kbvar_split.length; ++ind) {
								
								if(kbvar_split[ind].length()==1 && Character.isLowerCase(kbvar_split[ind].charAt(0)) || qryarg_split[ind].length()==1 && Character.isLowerCase(argument.charAt(ind))) {
									sub[ind]=kbvar_split[ind];
									substiCheck.put(qryarg_split[ind], kbvar_split[ind]);								
									subMap.put(qryarg_split[ind], kbvar_split[ind]);
								}
								
								if(qryarg_split[ind].equals(kbvar_split[ind])) {
									sub[ind] = kbvar_split[ind];
								}
							}
							
							for (int ind = 0; ind < sub.length; ++ind) {
								if (sub[ind] == null) {
									break label;
								}
							}
							
							List<String> newQryList = new LinkedList<String>();
							newQryList = changeVar(li);	
							
							String tt="";
							
							newQryList.remove(newQryList.get(i));
							if(newQryList.isEmpty()) {
								return "TRUE";
							} else {
								tt = substitute(newQryList, nums_arg++);
							}
							
							if(tt=="TRUE") {
								return "TRUE";
							} else {
								if(nums_arg > 1000)
									return "FALSE";
								break label;
							}
						}
//*****************************************************************************************		
						Iterator<Entry<String, LinkedList<String>>> iter2 = tempMap.entrySet().iterator();
						
						List<String> list5 = new LinkedList<String>();
						String[] kbvar_split1={};
						String mapValue1 = "";
						
						while(iter2.hasNext()) {
							String argument1 = "";
							Map.Entry pair1 = (Map.Entry)iter2.next();
							mapValue1 = pair1.getValue().toString();
							//System.out.println(mapValue1);	

							if(pair != pair1) {
								
								//System.out.println(mapValue1);	

								String constant1 = "";
								char[] char_kb1 = mapValue1.toCharArray();
								
								int k1=0;
								while(char_kb1[k1] != '(') {
									if(char_kb1[k1] == '[') {
										++k1;
										continue;
									}
									constant1 += char_kb1[k1];
									++k1;
								}
								//System.out.print(constant1);	
								
								for(int k2 = 0; k2 < mapValue1.length(); ++k2){
									if(char_kb1[k2]=='('){
										++k2;
										while(char_kb1[k2]!=')'){
											argument1 += char_kb1[k2++];	
										}
									}
								}
								
								kbvar_split1 = argument1.split("\\,");	
				
								String ab= constant1 + '(';
								String unify = unification(kbvar_split, kbvar_split1, qryarg_split);
								ab=ab+unify;
								ab=ab+')';
								
								list5.add(ab);
							}
						}
						
						String t = substitute(list5, nums_arg++);
						if(t.equals("TRUE")) {
							if(i == li.size() - 1) {
								return "TRUE";
							} else {
								List<String> list7=changeVar(li);
								li.clear();

								for(int x = 0; x < list7.size(); ++x) {
									String aaa=list7.get(x);
									li.add(aaa);
								}
								break outer;
							}
						} else {

							if(nums_arg > 10) {
								return "FALSE";
							}
							
							if(m == compareList.size()-1)
								return "FALSE";
						}	

					} else {
						if(m == compareList.size()-1 && !iter.hasNext())
							return "FALSE";
					}	
				}	
			}
		}
		return "FALSE";
	}

	
	
	
	
	private static List<String> changeVar(List<String> li) {
		List<String> newQryList = new LinkedList<String>();
		
		for(int i = 0; i < li.size(); ++i) {
			String s = li.get(i);			
			char[] char_query = s.toCharArray();
			
			String constant = "", argument = "";
			
			//get the constant
			int index = 0;
			while (index < char_query.length) {
				if (char_query[index] == '(') {
					index++;
					break;
				}
				constant += char_query[index];
				index++;
			}
			
			//get the argument, split each argument then store it in qryarg_split
			for (int j = index; j < char_query.length; ++j) {
				if (char_query[j] ==  ')') {
					break;
				}
				if (char_query[j] == ' ') {
					continue;
				}
				argument += char_query[j];
			}
			String[] qryarg_split = argument.split("\\,");
			
			//make the substitution between query and kb sentences
			String[] sub = new String[qryarg_split.length];
			for(int k = 0; k < qryarg_split.length; ++k){
				sub[k] = qryarg_split[k];
				if(substiCheck.containsKey(qryarg_split[k])){
					sub[k] = substiCheck.get(qryarg_split[k]);
				}
			}
			
			String new_query = constant + '(';
			for(int k = 0; k < sub.length; ++k) {
				if(k == 0)
					new_query += sub[k];
				else
					new_query = new_query + ',' + sub[k];
			}
			new_query += ')';
			newQryList.add(new_query);	
		}
			
		return newQryList;				
	}
	
	
	
	public static String unification(String[] kbvar_split,String[] kbvar_split1,String[] qryarg_split){
		String res = "";
		String[] sub = new String[kbvar_split1.length];
		
		for(int i = 0; i < kbvar_split1.length; ++i) {
			for(int j = 0; j < kbvar_split.length; ++j) {
				if(kbvar_split1[i].equals(kbvar_split[j])) {
					sub[i] = qryarg_split[j];
				}
			}
		}
		
		for(int i = 0; i < sub.length; ++i) { 
			if(sub[i] == null) {
				sub[i] = kbvar_split1[i];
			}
		}
		
		for(int i = 0; i < sub.length; ++i) {
			if(i == 0) {
				res += sub[i];
			} else {
				res = res + ','+ sub[i];
			}
		}
		return res;
	}
	
	
}