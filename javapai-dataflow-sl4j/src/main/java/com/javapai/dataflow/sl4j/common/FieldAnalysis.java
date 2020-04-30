//package com.sinafenqi.log.common;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class FieldAnalysis {
//	public static Map<String,Object> getFiledValue(Map<String, String> regexTemplet, String msgMatch){
//		Map<String,Object> fieldValues = new HashMap<String,Object>();
//		
//		for(String key : regexTemplet.keySet() ){
//			String value = getMatchedString(regexTemplet.get(key), msgMatch,2);
//			if(null != value){
//				fieldValues.put(key, value);
//			}
//		}
//		return fieldValues;
//	}
//	
//	
//	 /**
//     * 从文本text中找到regex首次匹配的字符串，不区分大小写
//     * @param regex： 正则表达式
//     * @param text：欲查找的字符串
//     * @return regex首次匹配的字符串，如未匹配返回空
//     */
//    private static String getMatchedString(String regex,String text,int groupID){
//        Pattern pattern=Pattern.compile(regex);
//        Matcher matcher=pattern.matcher(text);
//        while(matcher.find()){
//            return matcher.group(groupID);
//        }
//        return null;
//    }
//    
//    public static void main(String [] args){
//    	Map<String, String> regexTemplet = new HashMap<>();
////    	regexTemplet.put("MCH_NO", "(\\s,\"mchNO\"\\s:\\s\")(.+)(\")");
//    	//(.+?)最小匹配
//    	//(.+)最大匹配
//    	regexTemplet.put("MCH_NO", "(,\"mchNo\":\")(.+?)\"");
//    	//,"mchNo":"000100000000006"
//    	String msg = "16:03:58.355 [JobRunnerPool-thread-17] INFO c.r.s.task.ContinueRepeatJobManager - 持续处理-执行任务-taskId:24f7fafd3977286d3174fd404a215917-cp000002,任务信息:{\"cron\":false,\"extParams\":{\"busKey\":\"24f7fafd3977286d3174fd404a215917\",\"notifyHookName\":\"cp_CloudMchNotifyHandler\",\"busType\":\"cp000002\",\"notifyTimes\":\"8\",\"jobRunner\":\"core_coninue_repeat_job_runner\",\"curTime\":\"1\",\"notifyContent\":\"{\"tradeType\":\"1\",\"mchOrderNo\":\"2002816540\",\"mchId\":209,\"notifyUrl\":\"http://210.74.2.77:8082/ebank/chn/back/GDNY_BANK_MERGE/09\",\"mchNo\":\"000100000000006\"}\",\"notifyQueuePrefix\":\"MP:CLOUD:TRADE:TRADE-ORDER-NOTIFY:TIME\"},\"maxRetryTimes\":0,\"needFeedback\":false,\"priority\":100,\"relyOnPrevCycle\":true,\"repeatCount\":0,\"repeatInterval\":0,\"repeatable\":false,\"replaceOnExist\":false,\"submitNodeGroup\":\"cspaying_jobClient\",\"taskId\":\"24f7fafd3977286d3174fd404a215917-cp000002\",\"taskTrackerNodeGroup\":\"cspaying_TaskTracker\",\"triggerTime\":1481184237638} ";
//    	FieldAnalysis.getFiledValue(regexTemplet,msg);
//
//    }
//}
