package org.homelinux.tapiri.jei.essem;


import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

public class AutoFitTextView extends EditText {

public AutoFitTextView(Context context) {
    super(context);
    init();
}

public AutoFitTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
}

private void init() {

    maxTextSize = this.getTextSize();
    if (maxTextSize < 35) {
        maxTextSize = 30;
    }
    minTextSize = 20;
}

private void refitText(String text, int textWidth, int textHeight) {
	if (textWidth > 0) {
		// Subtract a little extra space to avoid word wrapping
        int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight() - 80;
        int availableHeight = textHeight - this.getPaddingTop() - this.getPaddingBottom() - 110;
        float trySize = maxTextSize;
        
        Rect bounds = new Rect();

        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        this.getPaint().getTextBounds(text, 0 , text.length(), bounds);
        while ((trySize > minTextSize)
                && ( (bounds.width() > availableWidth)
                || ((this.getLineHeight() * getOriginalLineCount()) > availableHeight) )) {
            trySize -= 1;
            if (trySize <= minTextSize) {
                trySize = minTextSize;
                break;
            }
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
            this.getPaint().getTextBounds(text, 0 , text.length(), bounds);
        }
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
    }
}

// EditText automatically wraps text, so we get the line count from the original input
private int getOriginalLineCount() {
	String fullText = this.getText().toString();
	String eol = System.getProperty("line.separator");
	
	int count = StringUtils.countMatches(fullText, eol) + 1;
	
	return count;
}

// Get the line which has the largest area
private String getLargestLine() {
	String fullText = this.getText().toString();
	String largestLine = "";
	String eol = System.getProperty("line.separator");
	String[] lines = fullText.split(eol);
	
	for (int i = 0; i < lines.length; i++) {
		if (this.getPaint().measureText(lines[i]) > this.getPaint().measureText(largestLine)) {
			largestLine = lines[i];
		}
	}
	
	return largestLine;
	
}

@Override
protected void onTextChanged(final CharSequence text, final int start,
        final int before, final int after) {
    refitText(getLargestLine(), this.getWidth(), this.getHeight());
}

@Override
protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    if ((w != oldw) || (h != oldh)) {
        refitText(getLargestLine(), this.getWidth(), this.getHeight());
    }
}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
    int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
    refitText(getLargestLine(), parentWidth, parentHeight);
}

public float getMinTextSize() {
    return minTextSize;
}

public void setMinTextSize(int minTextSize) {
    this.minTextSize = minTextSize;
}

public float getMaxTextSize() {
    return maxTextSize;
}

public void setMaxTextSize(int minTextSize) {
    this.maxTextSize = minTextSize;
}

private float minTextSize;
private float maxTextSize;

}
