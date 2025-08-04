"use client"

import type React from "react"

import { useState } from "react"
import { Card, CardBody, CardHeader, Input, Textarea, Select, SelectItem, Button, Slider } from "@nextui-org/react"
import { MapPin, DollarSign, Clock, Tag } from "lucide-react"
import NavigationBar from "@/components/Navbar"
import { useAuth } from "@/contexts/AuthContext"
import { useRouter } from "next/navigation"

export default function CreateTaskPage() {
  const { user, token } = useAuth()
  const router = useRouter()
  const [isLoading, setIsLoading] = useState(false)
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    category: "",
    location: "",
    budget: 50,
    urgency: "MEDIUM",
    requirements: "",
  })

  const categories = [
    { key: "shopping", label: "Shopping" },
    { key: "cleaning", label: "Cleaning" },
    { key: "delivery", label: "Delivery" },
    { key: "handyman", label: "Handyman" },
    { key: "pet-care", label: "Pet Care" },
    { key: "moving", label: "Moving" },
    { key: "other", label: "Other" },
  ]

  const urgencyLevels = [
    { key: "LOW", label: "Low - Flexible timing" },
    { key: "MEDIUM", label: "Medium - Within a few days" },
    { key: "HIGH", label: "High - ASAP" },
  ]

  const handleInputChange = (field: string, value: any) => {
    setFormData((prev) => ({ ...prev, [field]: value }))
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setIsLoading(true)

    try {
      const taskData = {
        title: formData.title,
        description: formData.description,
        category: formData.category,
        location: formData.location,
        budget: formData.budget,
        urgency: formData.urgency,
        requirements: formData.requirements,
        userId: user?.id,
        status: "PENDING",
      }

      const response = await fetch("https://errand-go.railway.internal/api/tasks", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(taskData),
      })

      if (response.ok) {
        router.push("/dashboard?message=Task created successfully!")
      } else {
        console.error("Failed to create task")
      }
    } catch (error) {
      console.error("Error creating task:", error)
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <NavigationBar />

      <div className="max-w-4xl mx-auto p-6">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">Create New Task</h1>
          <p className="text-gray-600">Tell us what you need help with</p>
        </div>

        <Card>
          <CardHeader>
            <h2 className="text-xl font-semibold">Task Details</h2>
          </CardHeader>
          <CardBody>
            <form onSubmit={handleSubmit} className="space-y-6">
              <div className="grid md:grid-cols-2 gap-6">
                <Input
                  label="Task Title"
                  placeholder="e.g., Grocery shopping at Walmart"
                  value={formData.title}
                  onChange={(e) => handleInputChange("title", e.target.value)}
                  startContent={<Tag className="w-4 h-4 text-gray-400" />}
                  isRequired
                />

                <Select
                  label="Category"
                  placeholder="Select a category"
                  value={formData.category}
                  onChange={(e) => handleInputChange("category", e.target.value)}
                  isRequired
                >
                  {categories.map((category) => (
                    <SelectItem key={category.key} value={category.key}>
                      {category.label}
                    </SelectItem>
                  ))}
                </Select>
              </div>

              <Textarea
                label="Description"
                placeholder="Provide detailed information about what needs to be done..."
                value={formData.description}
                onChange={(e) => handleInputChange("description", e.target.value)}
                minRows={4}
                isRequired
              />

              <div className="grid md:grid-cols-2 gap-6">
                <Input
                  label="Location"
                  placeholder="e.g., Downtown, Midtown, or specific address"
                  value={formData.location}
                  onChange={(e) => handleInputChange("location", e.target.value)}
                  startContent={<MapPin className="w-4 h-4 text-gray-400" />}
                  isRequired
                />

                <Select
                  label="Urgency"
                  placeholder="How urgent is this task?"
                  value={formData.urgency}
                  onChange={(e) => handleInputChange("urgency", e.target.value)}
                  startContent={<Clock className="w-4 h-4 text-gray-400" />}
                  isRequired
                >
                  {urgencyLevels.map((level) => (
                    <SelectItem key={level.key} value={level.key}>
                      {level.label}
                    </SelectItem>
                  ))}
                </Select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Budget: ${formData.budget}</label>
                <Slider
                  size="lg"
                  step={5}
                  color="primary"
                  showSteps={false}
                  showTooltip={true}
                  showOutline={true}
                  disableThumbScale={true}
                  formatOptions={{ style: "currency", currency: "USD" }}
                  tooltipValueFormatOptions={{
                    style: "currency",
                    currency: "USD",
                    minimumFractionDigits: 0,
                    maximumFractionDigits: 0,
                  }}
                  minValue={10}
                  maxValue={500}
                  value={formData.budget}
                  onChange={(value) => handleInputChange("budget", Array.isArray(value) ? value[0] : value)}
                  className="max-w-md"
                />
              </div>

              <Textarea
                label="Special Requirements (Optional)"
                placeholder="Any specific requirements, tools needed, or additional information..."
                value={formData.requirements}
                onChange={(e) => handleInputChange("requirements", e.target.value)}
                minRows={3}
              />

              <div className="flex gap-4 pt-4">
                <Button
                  type="submit"
                  color="primary"
                  size="lg"
                  isLoading={isLoading}
                  startContent={<DollarSign className="w-4 h-4" />}
                >
                  Post Task
                </Button>
                <Button type="button" variant="flat" size="lg" onClick={() => router.back()}>
                  Cancel
                </Button>
              </div>
            </form>
          </CardBody>
        </Card>
      </div>
    </div>
  )
}
