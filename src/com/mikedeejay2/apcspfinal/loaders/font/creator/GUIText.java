package com.mikedeejay2.apcspfinal.loaders.font.creator;

import com.mikedeejay2.apcspfinal.graphics.font.TextMaster;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Represents a piece of text in the game.
 *
 * @author Karl
 *
 */
public class GUIText {

	private String textString;
	private float fontSize;

	private int textMeshVao;
	private int vertexCount;
	private Vector3f color = new Vector3f(0f, 0f, 0f);
	private Vector3f shadowColor = new Vector3f(0f, 0f, 0f);

	private Vector2f position;
	private float lineMaxSize;
	private int numberOfLines;

	private FontType font;

	private TextMeshData textMeshData;

	private boolean centerText = false;
	private boolean hasShadow = false;
	private int[] vbos;

	/**
	 * Creates a new text, loads the text's quads into a VAO, and adds the text
	 * to the screen.
	 *
	 * @param text
	 *            - the text.
	 * @param fontSize
	 *            - the font size of the text, where a font size of 1 is the
	 *            default size.
	 * @param font
	 *            - the font that this text should use.
	 * @param position
	 *            - the position on the screen where the top left corner of the
	 *            text should be rendered. The top left corner of the screen is
	 *            (0, 0) and the bottom right is (1, 1).
	 * @param maxLineLength
	 *            - basically the width of the virtual page in terms of screen
	 *            width (1 is full screen width, 0.5 is half the width of the
	 *            screen, etc.) Text cannot go off the edge of the page, so if
	 *            the text is longer than this length it will go onto the next
	 *            line. When text is centered it is centered into the middle of
	 *            the line, based on this line length value.
	 * @param centered
	 *            - whether the text should be centered or not.
	 */
	public GUIText(String text, float fontSize, FontType font, Vector2f position, float maxLineLength,
				   boolean centered) {
		textMeshData = new TextMeshData(new float[0], new float[0]);
		this.textString = text;
		this.fontSize = fontSize;
		this.font = font;
		this.position = position;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		TextMaster.loadText(this);
	}

	public void setText(String s)
	{
		textString = s;
	}

	/**
	 * Remove the text from the screen.
	 */
	public void remove() {
		TextMaster.removeText(this);
	}

	/**
	 * @return The font used by this text.
	 */
	public FontType getFont() {
		return font;
	}

	/**
	 * Set the color of the text.
	 *
	 * @param r
	 *            - red value, between 0 and 1.
	 * @param g
	 *            - green value, between 0 and 1.
	 * @param b
	 *            - blue value, between 0 and 1.
	 */
	public void setColor(float r, float g, float b) {
		color.set(r, g, b);
	}

	public void setShadowColor(float r, float g, float b) {
		shadowColor.set(r, g, b);
	}

	public void setHasShadow(boolean hasShadow)
	{
		this.hasShadow = hasShadow;
	}

	/**
	 * @return the color of the text.
	 */
	public Vector3f getColor() {
		return color;
	}

	/**
	 * @return The number of lines of text. This is determined when the text is
	 *         loaded, based on the length of the text and the max line length
	 *         that is set.
	 */
	public int getNumberOfLines() {
		return numberOfLines;
	}

	/**
	 * @return The position of the top-left corner of the text in screen-space.
	 *         (0, 0) is the top left corner of the screen, (1, 1) is the bottom
	 *         right.
	 */
	public Vector2f getPosition() {
		return position;
	}

	/**
	 * @return the ID of the text's VAO, which contains all the vertex data for
	 *         the quads on which the text will be rendered.
	 */
	public int getMesh() {
		return textMeshVao;
	}

	/**
	 * Set the VAO and vertex count for this text.
	 *
	 * @param vao
	 *            - the VAO containing all the vertex data for the quads on
	 *            which the text will be rendered.
	 * @param verticesCount
	 *            - the total number of vertices in all of the quads.
	 */
	public void setMeshInfo(int vao, int verticesCount, int[] vbos) {
		this.textMeshVao = vao;
		this.vertexCount = verticesCount;
		this.vbos = vbos;
	}

	/**
	 * @return The total number of vertices of all the text's quads.
	 */
	public int getVertexCount() {
		return this.vertexCount;
	}

	/**
	 * @return the font size of the text (a font size of 1 is normal).
	 */
	public float getFontSize() {
		return fontSize;
	}

	/**
	 * Sets the number of lines that this text covers (method used only in
	 * loading).
	 *
	 * @param number
	 */
	public void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}

	/**
	 * @return {@code true} if the text should be centered.
	 */
	public boolean isCentered() {
		return centerText;
	}

	public Vector3f getShadowColor()
	{
		return shadowColor;
	}

	public boolean hasShadow()
	{
		return hasShadow;
	}

	/**
	 * @return The maximum length of a line of this text.
	 */
	public float getMaxLineSize() {
		return lineMaxSize;
	}

	/**
	 * @return The string of text.
	 */
	public String getTextString() {
		return textString;
	}

	public void setTextString(String textString)
	{
		this.textString = textString;
	}

	public void setFontSize(float fontSize)
	{
		this.fontSize = fontSize;
	}

	public int getTextMeshVao()
	{
		return textMeshVao;
	}

	public void setTextMeshVao(int textMeshVao)
	{
		this.textMeshVao = textMeshVao;
	}

	public void setVertexCount(int vertexCount)
	{
		this.vertexCount = vertexCount;
	}

	public void setColor(Vector3f color)
	{
		this.color = color;
	}

	public void setPosition(Vector2f position)
	{
		this.position = position;
	}

	public float getLineMaxSize()
	{
		return lineMaxSize;
	}

	public void setLineMaxSize(float lineMaxSize)
	{
		this.lineMaxSize = lineMaxSize;
	}

	public void setFont(FontType font)
	{
		this.font = font;
	}

	public boolean isCenterText()
	{
		return centerText;
	}

	public void setCenterText(boolean centerText)
	{
		this.centerText = centerText;
	}

	public TextMeshData getTextMeshData()
	{
		return textMeshData;
	}

	public void setTextMeshData(TextMeshData textMeshData)
	{
		this.textMeshData = textMeshData;
	}

	public int[] getVbos()
	{
		return vbos;
	}
}
