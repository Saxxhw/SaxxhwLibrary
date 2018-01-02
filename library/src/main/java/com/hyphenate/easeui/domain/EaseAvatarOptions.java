package com.hyphenate.easeui.domain;

/**
 * Created by wei on 2016/11/29.
 */

public class EaseAvatarOptions {
    private int avatarShape;
    private int avatarRadius;
    private int avatarBorderColor;
    private int avatarBorderWidth;

    public int getAvatarShape() {
        return avatarShape;
    }

    /**
     * 设置item中的头像形状
     * 0：默认，1：圆形，2：矩形
     * @param
     */
    public void setAvatarShape(int avatarShape) {
        this.avatarShape = avatarShape;
    }

    public int getAvatarRadius() {
        return avatarRadius;
    }

    /**
     * 设置倒角
     *
     * @param
     */
    public void setAvatarRadius(int avatarRadius) {
        this.avatarRadius = avatarRadius;
    }

    public int getAvatarBorderColor() {
        return avatarBorderColor;
    }

    /**
     * 设置头像控件边框颜色
     *
     * @param
     */
    public void setAvatarBorderColor(int avatarBorderColor) {
        this.avatarBorderColor = avatarBorderColor;
    }

    public int getAvatarBorderWidth() {
        return avatarBorderWidth;
    }

    /**
     * 设置头像控件边框宽度
     *
     * @param
     */
    public void setAvatarBorderWidth(int avatarBorderWidth) {
        this.avatarBorderWidth = avatarBorderWidth;
    }

}
