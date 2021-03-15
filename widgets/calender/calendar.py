from PyQt5.QtGui import QPalette, QTextCharFormat
from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QApplication, QCalendarWidget

class MyCalendar(QCalendarWidget):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Calendar")
        self.setGridVisible(False);
        self.setNavigationBarVisible(True)
        self.setVerticalHeaderFormat(0)
        self.move(1215,36)
        self.resize(700,475)
        #self.setNavigationBarVisible(0)

if __name__ == "__main__":
    app = QApplication([])
    calendar = MyCalendar()
    calendar.show()
    app.exec()
