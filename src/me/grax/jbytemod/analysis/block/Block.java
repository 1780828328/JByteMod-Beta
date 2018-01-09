package me.grax.jbytemod.analysis.block;

import java.util.ArrayList;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;

public class Block {
  private ArrayList<Block> output = new ArrayList<>();

  /**
   * when output is added automatically add input
   **/
  private ArrayList<Block> input = new ArrayList<>();

  private AbstractInsnNode endNode;
  private ArrayList<AbstractInsnNode> nodes = new ArrayList<>();

  private LabelNode label;

  public Block() {
    super();
  }

  public AbstractInsnNode getEndNode() {
    return endNode;
  }

  public void setEndNode(AbstractInsnNode endNode) {
    this.endNode = endNode;
  }

  public ArrayList<Block> getOutput() {
    return output;
  }

  public void setOutput(ArrayList<Block> output) {
    this.output = output;
  }

  public ArrayList<Block> getInput() {
    return input;
  }

  public void setInput(ArrayList<Block> input) {
    this.input = input;
  }

  public LabelNode getLabel() {
    return label;
  }

  public void setLabel(LabelNode label) {
    this.label = label;
  }

  public ArrayList<AbstractInsnNode> getNodes() {
    return nodes;
  }

  public void setNodes(ArrayList<AbstractInsnNode> nodes) {
    this.nodes = nodes;
  }

  public boolean endsWithJump() {
    return endNode instanceof JumpInsnNode;
  }

}