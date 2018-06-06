package com.aleexf.gui

import java.awt.Button
import java.awt.Checkbox
import java.awt.event.ActionListener
import java.awt.event.ItemListener
import javax.swing.JFrame

import com.aleexf.gui.NicknameField
import com.aleexf.gui.IpAddressField
import com.aleexf.logging.Logger


class MainMenu:Thread() {
    var nick:String? = null
    var IpAddress:String? = null
    private val window = JFrame("Bomberman: Menu")
    private val startButton = Button("Start")
    private val ipTextField = IpAddressField(15, 30, 200, 20)
    private val nickTextField = NicknameField(15, 5, 200, 20)
    private val localhostCheckbox = Checkbox("Create server on localhost")
    init {
        startButton.setBounds(70, 80, 100, 20)
        startButton.addActionListener(ActionListener() {
            if (IpAddress == null && ipTextField.textField.text.contains('*')) {
                Logger.error("Error: Incorrect ip address found",
                        "Enter an ip address or create your own server",
                        false)
            } else if (nickTextField.textField.text == "Enter your nickname...") {
                Logger.error("Error: Incorrect nickname found",
                        "Enter your nickname",
                        false)
            } else {
                window.isVisible = false
                window.dispose()
                if (IpAddress == null) {
                    IpAddress = ipTextField.textField.text
                }
                nick = nickTextField.textField.text
            }
        })
        localhostCheckbox.setBounds(15, 55, 175, 15)
        localhostCheckbox.addItemListener(ItemListener {
            it.let {
                IpAddress = when(IpAddress) {
                    "127.0.0.1" -> null
                    else -> "127.0.0.1"
                }
            }
        })



        window.add(startButton)
        window.add(localhostCheckbox)
        window.add(ipTextField.textField)
        window.add(nickTextField.textField)

        window.setSize(250, 150)
        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        window.layout = null
        window.isResizable = false
        window.isVisible = true

        this.run()
    }
    override fun run() {
        Logger.info("Process main menu started")
        while (window.isVisible) {
            sleep(100)
        }
    }
}
