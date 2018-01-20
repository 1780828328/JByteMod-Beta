package me.grax.jbytemod.utils.task;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.swing.SwingWorker;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import me.grax.jbytemod.JByteMod;
import me.grax.jbytemod.JarArchive;
import me.grax.jbytemod.ui.PageEndPanel;
import me.lpk.util.JarUtils;

public class SaveTask extends SwingWorker<Void, Integer> {

  private File output;
  private PageEndPanel jpb;
  private JByteMod jbm;
  private JarArchive file;

  public SaveTask(JByteMod jbm, File output, JarArchive file) {
    this.output = output;
    this.jbm = jbm;
    this.file = file;
    this.jpb = jbm.getPP();
  }

  @Override
  protected Void doInBackground() throws Exception {
    Map<String, ClassNode> classes = this.file.getClasses();
    Map<String, byte[]> outputBytes = this.file.getOutput();
    int flags = JByteMod.ops.get("compute_maxs").getBoolean() ? 1 : 0;
    System.out.println("Writing..");
    if(this.file.isSingleEntry()) {
      ClassNode node = classes.values().iterator().next();
      ClassWriter writer = new ClassWriter(flags);
      node.accept(writer);
      publish(50);
      System.out.println("Saving..");
      Files.write(this.output.toPath(), writer.toByteArray());
      publish(100);
      System.out.println("Done!");
      return null;
    }
    publish(0);
    double size = classes.keySet().size();
    double i = 0;
    for (String s : classes.keySet()) {
      ClassNode node = classes.get(s);
      ClassWriter writer = new ClassWriter(flags);
      node.accept(writer);
      outputBytes.put(s, writer.toByteArray());
      publish((int) ((i++ / size) * 50d));
    }
    publish(50);
    System.out.println("Saving..");
    JarUtils.saveAsJar(outputBytes, output.getAbsolutePath());
    publish(100);
    System.out.println("Done!");
    return null;
  }

  @Override
  protected void process(List<Integer> chunks) {
    int i = chunks.get(chunks.size() - 1);
    jpb.setValue(i);
    super.process(chunks);
  }

}