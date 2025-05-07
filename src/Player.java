import java.awt.*;
import java.awt.image.BufferedImage;

/**
	Esta classe representa os jogadores (players) do jogo. A classe principal do jogo
	instancia dois objetos deste tipo quando a execução é iniciada.
*/
public class Player implements ISolid {
	private double direction;
	private double cx, cy, width, height, speed, angle;
	private Color color;
	private String id;
	private BufferedImage img;
	private boolean dead, hasFired, someoneDied;
	private Shot shot;

	/**
		Construtor da classe Player.

		@param cx coordenada x da posição inicial do player (centro do retângulo que o representa).
		@param cy coordenada y da posição inicial do player (centro do retângulo que o representa).
		@param width largura do retângulo que representa o player.
		@param height altura do retângulo que representa o player.
		@param angle direção apontada pelo player
		@param sprite gráfico do player
		@param color cor do player.
		@param id uma string que identifica o player
		@param speed velocidade do movimento do player.
	*/
	public Player(double cx, double cy, double width, double height, double angle, BufferedImage sprite, Color color, String id, double speed){
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.angle = angle;
		this.color = color;
		this.id = id;
		this.direction = angle;
		this.speed = speed;
		this.img = sprite;
		this.dead = false;
		this.hasFired = false;
		this.someoneDied = false;
		this.shot = null;
	}

	/**
		Método chamado sempre que o player precisa ser (re)desenhado.
	*/
	public void draw(){
		GameLib.setColor(color);
		if (isDead()) {
			long now = System.currentTimeMillis();
			GameLib.drawImage(img, cx, cy, direction + now % 60 * 60, width / img.getWidth());
			return;
		}
		GameLib.drawImage(img, cx, cy, direction, width / img.getWidth());
	}

	/**
		Método chamado quando se deseja mover para a frente na direção atual. 
		Este método é chamado sempre que a tecla associada à ação 
		de mover o player para frente estiver pressionada.

		@param delta quantidade de milissegundos que se passou entre o ciclo anterior de atualização do jogo e o atual.
	*/
	public void moveForwards(long delta){
		if(isDead() == true || this.someoneDied == true) return;
    	double dx = Math.cos(Math.toRadians(direction)) * speed * delta;
    	double dy = Math.sin(Math.toRadians(direction)) * speed * delta;
    	this.cx += dx;
    	this.cy += dy;
	}

	/**
		Método chamado quando se deseja mover o player para trás na direção atual. 
		Este método é chamado sempre que a tecla associada à ação 
		de mover o player para trás estiver pressionada.

		@param delta quantidade de milissegundos que se passou entre o ciclo anterior de atualização do jogo e o atual.
	*/
	public void moveBackwards(long delta){
		if(isDead() == true || this.someoneDied == true) return;
		double deltaSeconds = delta / 1000.0;
    	double dx = Math.cos(Math.toRadians(direction)) * speed * delta;
    	double dy = Math.sin(Math.toRadians(direction)) * speed * delta;
    	this.cx -= dx;
    	this.cy -= dy;
	}

	/**
		Método chamado quando se deseja girar o player para a esquerda (sentido anti-horário). 
		Este método é chamado sempre que a tecla associada à ação 
		de rotacionar o player à esquerda estiver pressionada.

		@param delta quantidade de milissegundos que se passou entre o ciclo anterior de atualização do jogo e o atual.
	 */
	public void rotateLeft(long delta) {
		if(isDead() == true || this.someoneDied == true) return;
    	this.direction -= speed * delta;
		if (direction < 0) {
			direction += 360;
		}
	}

	/**
		Método chamado quando se deseja girar o player para a direita (sentido horário). 
		Este método é chamado sempre que a tecla associada à ação 
		de rotacionar o player à direita estiver pressionada.

		@param delta quantidade de milissegundos que se passou entre o ciclo anterior de atualização do jogo e o atual.
	 */
	public void rotateRight(long delta) {
		if(isDead() == true || this.someoneDied == true) return;
    	this.direction += speed * delta;
		if (direction > 360) {
			direction -= 360;
		}
	}

	/**
	 * Retorna se o jogador pode realizar um disparo ou não. Chamado sempre que a ação de disparar
	 * desse player for acionada.
	 */
	public boolean canFire() {
		if(isDead() == false && someoneDied == false && (shot == null || shot.isActive() == false)) return true;
		else return false; 
	}

	/**
	 * Cria um disparo vindo desse player na mesma direção apontada pelo player.
	 * Esse método cria o disparo e o adiciona ao jogo.
	 */
	public void fire() {
		Shot shot = new Shot(this, this.cx, this.cy, 1.5, this.direction, 5*speed);
		this.shot = shot;
		Combat.addShot(shot);
	}

	/**
	 * Retorna se o player acabou de ser destruído. Enquanto o player estiver destruído, ele não
	 * poderá ser danificado novamente.
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Chamado sempre que o player for destruído com um disparo. 
	 */
	public void die() {
		this.dead = true;
		new Thread(() -> {
			try {
				Thread.sleep(2000); 
				this.dead = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	/**
	 * Chamado sempre que um dos players forem atingidos
	 */
	public void someoneIsDead() {
		someoneDied = true;
		new Thread(() -> {
			try {
				Thread.sleep(2000); 
				someoneDied = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	/**
		Método que devolve a string de identificação do player.
		@return a String de identificação.
	*/
	public String getId() { 
		return this.id; 
	}

	/**
	 * Teleporta o player para essa coordenada do mapa.
	 */
	public void setPosition(double cx, double cy) {
		this.cx = cx;
		this.cy = cy;
	}

	/**
		Método que devolve a largura do retângulo que representa o player.
		@return um double com o valor da largura.
	*/
	public double getWidth() { 
		return this.width; 
	}

	/**
		Método que devolve a altura do retângulo que representa o player.
		@return um double com o valor da altura.
	*/
	public double getHeight() { 
		return this.height;
	}

	/**
		Método que devolve a coordenada x do centro do retângulo que representa o player.
		@return o valor double da coordenada x.
	*/
	public double getCx() { 
		return this.cx;
	}

	/**
		Método que devolve a coordenada y do centro do retângulo que representa o player.
		@return o valor double da coordenada y.
	*/
	public double getCy() { 
		return this.cy;
	}

	/**
		Método que devolve se o outro player está morto.
		@return o valor bool da variavel someoneDied.
	*/
	public boolean getSomeoneDied() {
		return this.someoneDied;
	}

	/**
	 * Obtém a cor do player.
	 */
	public Color getColor() {
		return this.color;
	}
}

