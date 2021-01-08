package com.justbon.lps.broadcast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.flink.api.common.state.BroadcastState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.runtime.state.HeapBroadcastState;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.justbon.lps.config.ServiceConfig;
import com.justbon.lps.constants.Constants;
import com.justbon.lps.entity.MetaEntity;
/**
 * @author xiesc
 * @date 2020年8月3日
 * @version 1.0.0
 * @Description: 日志元数据广播流
 */
public class LogMetaDataBroadcast extends BroadcastProcessFunction<String, MetaEntity, JsonObject>{
	
	/**   
	 * @Fields serialVersionUID
	 */ 
	private static final long serialVersionUID = 2814571470524995812L;
	private final MapStateDescriptor<String, MetaEntity> metaDataDescriptor;
	private static final String LOGTYPE = "logType";
	private static final String METACODE = "metaCode";
	private static final String SYSNAME = "sysName";
	private static final ServiceConfig config = ServiceConfig.getInstance();
	private static final String OPERLOG = config.getAsString(Constants.LPS_OPERLOG_NAME);
	private static final String BEHAVIOURLOG = config.getAsString(Constants.LPS_BEHAVIOURLOG_NAME);
	private static final String SYSLOG = config.getAsString(Constants.LPS_SYSLOG_NAME);
	private static final Logger logger = LogManager.getLogger(LogMetaDataBroadcast.class);
	private static final Gson gson = new Gson();
	private final OutputTag<JsonObject> syslogTag;
	private final OutputTag<JsonObject> behalogTag;
	private final OutputTag<JsonObject> operlogTag;
	
	public LogMetaDataBroadcast(MapStateDescriptor<String, MetaEntity> metaDataDescriptor,
			OutputTag<JsonObject> syslogTag,
			OutputTag<JsonObject> behalogTag,
			OutputTag<JsonObject> operlogTag){
		this.metaDataDescriptor = metaDataDescriptor;
		this.syslogTag = syslogTag;
		this.behalogTag = behalogTag;
		this.operlogTag = operlogTag;
	}
	
	@Override
	public void processBroadcastElement(MetaEntity meta,
			BroadcastProcessFunction<String, MetaEntity, JsonObject>.Context ctx,
			Collector<JsonObject> collect) throws Exception {
		BroadcastState<String, MetaEntity> state = ctx.getBroadcastState(metaDataDescriptor);
		state.put(meta.getBpCode(), meta);
	}

	/**
     * 去除字符串中的空格、回车、换行符、制表符等
     * @param str
     * @return
     */
    public static String replaceSpecialStr(String str) {
        String repl = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            repl = m.replaceAll("");
        }
        return repl;
    }

	
	@Override
	public void processElement(String json,
			BroadcastProcessFunction<String, MetaEntity, JsonObject>.ReadOnlyContext ctx,
			Collector<JsonObject> collect) throws Exception {
		JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
		JsonElement typeElement = jsonObject.get(LOGTYPE);
		JsonElement metaCodeElement = jsonObject.get(METACODE);
		JsonElement sysNameElement = jsonObject.get(SYSNAME);
		if(typeElement == null){
			logger.warn(" logType field required : "+json);
			return;
		}
		/*if(metaCodeElement == null){
			logger.warn(" metaCode field required : "+json);
			return;
		}*/
		if(sysNameElement == null){
			logger.warn(" sysName field required : "+json);
			return;
		}
		String type = typeElement.getAsString();
		// 处理日志元数据逻辑
		if(metaCodeElement != null){
			String metaCode = metaCodeElement.getAsString();
			if (!Strings.isEmpty(metaCode)) {
				HeapBroadcastState<String, MetaEntity> config = 
						(HeapBroadcastState<String, MetaEntity>) ctx.getBroadcastState(metaDataDescriptor);
				MetaEntity metaEntity = config.get(metaCode);
				if(metaEntity != null){
					jsonObject.add("metaData", gson.toJsonTree(metaEntity));
				}
			}
		}
		
		if (OPERLOG.equals(type)) {
			ctx.output(operlogTag, jsonObject);
		} else if (BEHAVIOURLOG.equals(type)) {
			ctx.output(behalogTag, jsonObject);
		} else if (SYSLOG.equals(type)) {
			ctx.output(syslogTag, jsonObject);
		}
	}
}
