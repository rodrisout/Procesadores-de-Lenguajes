private interface ICell {}
private class Cell<T> implements ICell {
	private T v;
	public Cell() { this.v = null; }
	private void set(T v) {
		this.v = v;
	}
	private T get() {
		return this.v;
	}
}

private class Memory {
	private List<ICell> cells;
	public Memory(int space) {
		this.cells = new ArrayList<>(space);
	}

	public ICell fetch(int dir) {
		if (dir >= this.cells.length())
			throw new MachineRuntimeException(
				"fetch() index " + dir
				+ " outside of memory range");

		return this.cells.get(dir);
	}

	public boolean store(int dir, Cell value) {
		if (dir >= this.cells.length())
			throw new MachineRuntimeException(
				"store() index " + dir
				+ " outside of memory range");

		this.cells.set(dir, value);
	}
}

private interface class Instruction { }

private class InsActiva implements Instruction {
	int level, espacio, retpc;
	public InsActiva(int level, int space, int retpc) {
		this.level = level;
		this.space = space;
		this.retpc = retpc;
	}
}

private class InsDesactiva implements Instruction {
	int level;
	public InsDesactiva(int level) {
		this.level = level;
	}
}

private class InsNew implements Instrucion { }

class Machine {
	private List<Instruction> instructions;

	public Machine() {
		this.instructions = new ArrayList<Instruction>();
	}

	private void emit(Instruction ins) {
		this.instructions.add(ins);
	}

	private void execute(int space) {
		Memory stackMemory = new Memory(space);
		List<List<Memory>> displays = new ArrayList<>();
		int pc = 0; int sp = 0;
		boolean running = true;
		while (running) {
			if (pc >= this.instructions.size())
				throw new MachineRuntimeException("PC out of range");

			Instruction ins = this.instructions.get(this.pc);
			switch (ins) {
			case InsIrA ia:
				pc = ia.pc;
				break;
			case InsStop is:
				running = false;
				break;
			case InsActiva ia:
				if (ia.level > displays.size())
					throw new MachineRuntimeException(
						"XXX bad 'activa' level");

				if (ia.level == displays.size())
					displays.add(new ArrayList<>());

				displays.get(ia.level).add(new Display(ia.space));
				++pc;
				break;
			case InsDesactiva da:
				++pc;
				break;
			case InsApilaInt ip:
				stackMemory.store(sp++, new Cell<int>().set(ip.v));
				++pc;
				break;
			case InsDesapila da:
				if (sp < da.n)
					throw new RuntimeException( "SP underflowing");
				sp -= da.n;
				break;
			default:
				throw new MachineRuntimeException(
						"XXX bad instruction '" + ins + "'");
			}
		}
	}
}
