package io.chesslave.mouth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableUtterance.class)
public interface Utterance {

    String text();
}

