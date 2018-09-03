package com.example.clova;

import com.linecorp.clova.extension.boot.handler.annnotation.*;
import com.linecorp.clova.extension.boot.message.response.CEKResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.linecorp.clova.extension.boot.message.speech.OutputSpeech.text;

@CEKRequestHandler
public class EchoHandler {

  private static final Logger log = LoggerFactory.getLogger(EchoHandler.class);

  @LaunchMapping
  CEKResponse handleLaunch() {
    return CEKResponse.builder()
      .outputSpeech(text("ぎしやまのカレースキルを起動しました。"))
      .shouldEndSession(false)
      .build();
  }

  @IntentMapping("CurreySearchIntent")
  CEKResponse handleRepeatIntent(@SlotValue Optional<String> area) {
    String outputSpeechText = area
      .map(this::callbackShop)
      .orElse("聞き取れませんでした。");
    return CEKResponse.builder()
      .outputSpeech(text(outputSpeechText))
      .shouldEndSession(false)
      .build();
  }

  private String callbackShop(String inArea) {
    switch (inArea) {
      case "札幌":
        return "おすすめは、アジャンタ札幌店です。";
      case "千歳":
        return "おすすめは、ラマイ千歳店です。";
      default:
        return "ごめんなさい、わかりませんでした。";
    }
  }

  @IntentMapping("Clova.CancelIntent")
  CEKResponse handleCancelIntent() {
    return CEKResponse.builder()
      .outputSpeech(text("ぎしやまのカレースキルを終了します。"))
      .shouldEndSession(true)
      .build();
  }

  @SessionEndedMapping
  CEKResponse handleSessionEnded() {
    log.info("ぎしやまのカレースキルを終了しました。");
    return CEKResponse.empty();
  }

}
