package com.huawei.hicloud.filter;

import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RulePackageMatchesAgendaFilter implements AgendaFilter {

    private final Pattern pattern;

    private final boolean accept;

    public RulePackageMatchesAgendaFilter(final String regexp) {
        this(regexp,true);
    }

    public RulePackageMatchesAgendaFilter(final String regexp, final boolean accept) {
        this.pattern = Pattern.compile(regexp);
        this.accept = accept;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public boolean isAccept() {
        return accept;
    }

    public boolean accept(Match activation) {
        Matcher matcher = pattern.matcher(activation.getRule().getPackageName());
        if (matcher.matches()) {
            return this.accept;
        } else {
            return !this.accept;
        }
    }
}
