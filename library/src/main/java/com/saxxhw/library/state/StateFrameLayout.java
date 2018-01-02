package com.saxxhw.library.state;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.saxxhw.library.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;

public class StateFrameLayout extends FrameLayout implements StateLayout {

    private static final String TAG_LOADING = "ProgressActivity.TAG_LOADING";
    private static final String TAG_EMPTY = "ProgressActivity.TAG_EMPTY";
    private static final String TAG_ERROR = "ProgressActivity.TAG_ERROR";

    final String CONTENT = "type_content";
    final String LOADING = "type_loading";
    final String EMPTY = "type_empty";
    final String ERROR = "type_error";

    LayoutInflater inflater;
    View view;
    LayoutParams layoutParams;
    Drawable currentBackground;

    List<View> contentViews = new ArrayList<>();

    FrameLayout loadingStateFrameLayout;

    FrameLayout emptyStateFrameLayout;
    TextView emptyStateTitleTextView;
    TextView emptyStateContentTextView;

    FrameLayout errorStateFrameLayout;
    TextView errorStateTitleTextView;
    TextView errorStateContentTextView;

    private String state = CONTENT;

    public StateFrameLayout(Context context) {
        super(context);
    }

    public StateFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StateFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        currentBackground = this.getBackground();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getTag() == null || (!child.getTag().equals(TAG_LOADING) && !child.getTag().equals(TAG_EMPTY) && !child.getTag().equals(TAG_ERROR))) {
            contentViews.add(child);
        }
    }

    /**
     * Hide all other states and show content
     */
    @Override
    public void showContent() {
        switchState(CONTENT, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Hide content and show the progress bar
     */
    @Override
    public void showLoading() {
        switchState(LOADING, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Show empty view when there are not data to show
     *
     * @param emptyTextTitle   Title of the empty view to show
     * @param emptyTextContent Content of the empty view to show
     * @param onClickListener  Listener of the error view button
     */
    @Override
    public void showEmpty(@Nullable String emptyTextTitle, @Nullable String emptyTextContent, OnClickListener onClickListener) {
        switchState(EMPTY, emptyTextTitle, emptyTextContent, onClickListener, Collections.<Integer>emptyList());
    }

    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     *
     * @param errorTextTitle   Title of the error view to show
     * @param errorTextContent Content of the error view to show
     */
    @Override
    public void showError(String errorTextTitle, String errorTextContent, OnClickListener onClickListener) {
        switchState(ERROR, errorTextTitle, errorTextContent, onClickListener, Collections.<Integer>emptyList());
    }

    /**
     * Get which state is set
     *
     * @return State
     */
    public String getState() {
        return state;
    }

    /**
     * Check if content is shown
     *
     * @return boolean
     */
    public boolean isContent() {
        return state.equals(CONTENT);
    }

    /**
     * Check if loading state is shown
     *
     * @return boolean
     */
    public boolean isLoading() {
        return state.equals(LOADING);
    }

    /**
     * Check if empty state is shown
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return state.equals(EMPTY);
    }

    /**
     * Check if error state is shown
     *
     * @return boolean
     */
    public boolean isError() {
        return state.equals(ERROR);
    }

    private void switchState(String state, String errorText, String errorTextContent, OnClickListener onClickListener, List<Integer> skipIds) {
        this.state = state;

        switch (state) {
            case CONTENT:
                //Hide all state views to display content
                setContentState(skipIds);
                break;
            case LOADING:
                setLoadingState(skipIds);
                break;
            case EMPTY:
                setEmptyState(skipIds);

                emptyStateTitleTextView.setText(!TextUtils.isEmpty(errorText) ? errorText : getContext().getText(R.string.state_empty_message));
                emptyStateContentTextView.setText(!TextUtils.isEmpty(errorTextContent) ? errorTextContent : getContext().getText(R.string.state_empty_desc));
                emptyStateFrameLayout.setOnClickListener(onClickListener);
                break;
            case ERROR:
                setErrorState(skipIds);

                errorStateTitleTextView.setText(R.string.state_error_message);
                errorStateContentTextView.setText(R.string.state_error_desc);
                errorStateFrameLayout.setOnClickListener(onClickListener);
                break;
        }
    }

    private void setLoadingView() {
        if (loadingStateFrameLayout == null) {
            view = inflater.inflate(R.layout.state_frame_layout_loading_view, null);
            loadingStateFrameLayout = view.findViewById(R.id.frame_layout_progress);
            loadingStateFrameLayout.setTag(TAG_LOADING);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = CENTER_IN_PARENT;

            addView(loadingStateFrameLayout, layoutParams);
        } else {
            loadingStateFrameLayout.setVisibility(VISIBLE);
        }
    }

    private void setEmptyView() {
        if (emptyStateFrameLayout == null) {
            view = inflater.inflate(R.layout.state_frame_layout_empty_view, null);
            emptyStateFrameLayout = view.findViewById(R.id.frame_layout_empty);
            emptyStateFrameLayout.setTag(TAG_EMPTY);

            emptyStateTitleTextView = view.findViewById(R.id.text_title);
            emptyStateContentTextView = view.findViewById(R.id.text_content);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = CENTER_IN_PARENT;

            addView(emptyStateFrameLayout, layoutParams);
        } else {
            emptyStateFrameLayout.setVisibility(VISIBLE);
        }
    }

    private void setErrorView() {
        if (errorStateFrameLayout == null) {
            view = inflater.inflate(R.layout.state_frame_layout_error_view, null);
            errorStateFrameLayout = view.findViewById(R.id.frame_layout_error);
            errorStateFrameLayout.setTag(TAG_ERROR);

            errorStateTitleTextView = view.findViewById(R.id.text_title);
            errorStateContentTextView = view.findViewById(R.id.text_content);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = CENTER_IN_PARENT;

            addView(errorStateFrameLayout, layoutParams);
        } else {
            errorStateFrameLayout.setVisibility(VISIBLE);
        }
    }

    /**
     * Helpers
     */

    private void setContentState(List<Integer> skipIds) {
        hideLoadingView();
        hideEmptyView();
        hideErrorView();

        setContentVisibility(true, skipIds);
    }

    private void setLoadingState(List<Integer> skipIds) {
        hideEmptyView();
        hideErrorView();

        setLoadingView();
        setContentVisibility(false, skipIds);
    }

    private void setEmptyState(List<Integer> skipIds) {
        hideLoadingView();
        hideErrorView();

        setEmptyView();
        setContentVisibility(false, skipIds);
    }

    private void setErrorState(List<Integer> skipIds) {
        hideLoadingView();
        hideEmptyView();

        setErrorView();
        setContentVisibility(false, skipIds);
    }

    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : contentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void hideLoadingView() {
        if (loadingStateFrameLayout != null) {
            loadingStateFrameLayout.setVisibility(GONE);
        }
    }

    private void hideEmptyView() {
        if (emptyStateFrameLayout != null) {
            emptyStateFrameLayout.setVisibility(GONE);
        }
    }

    private void hideErrorView() {
        if (errorStateFrameLayout != null) {
            errorStateFrameLayout.setVisibility(GONE);
        }
    }
}
